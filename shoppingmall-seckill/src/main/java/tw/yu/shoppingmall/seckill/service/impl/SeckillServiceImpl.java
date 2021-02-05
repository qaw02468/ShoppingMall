package tw.yu.shoppingmall.seckill.service.impl;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.yu.common.to.MemberResponseVo;
import tw.yu.common.to.mq.SecKillOrderTo;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.seckill.feign.CouponFeign;
import tw.yu.shoppingmall.seckill.feign.ProductFeign;
import tw.yu.shoppingmall.seckill.interceptor.LoginUserInterceptor;
import tw.yu.shoppingmall.seckill.service.SeckillService;
import tw.yu.shoppingmall.seckill.to.SeckillSkuRedisTo;
import tw.yu.shoppingmall.seckill.vo.SeckillSessionsWithSkus;
import tw.yu.shoppingmall.seckill.vo.SkuInfoVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author - a89010531111@gmail.com
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeign couponFeign;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String SESSION_CACHE_PREFIX = "seckill:sessions:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus:";
    private final String SKUSTOCK_SEMAPHONE = "seckill:stock:";

    @Override
    public void uploadSeckillSkuLatest3Day() {
        R r = couponFeign.getLate3DaySession();
        if (r.getCode() == 0) {
            List<SeckillSessionsWithSkus> sessions = r.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            saveSessionInfo(sessions);
            saveSessionSkuInfo(sessions);
        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {

        long time = new Date().getTime();
        try (Entry entry = SphU.entry("seckillSkus")) {
            Set<String> keys = stringRedisTemplate.keys(SESSION_CACHE_PREFIX + "*");
            for (String key : keys) {
                String replace = key.replace("seckill:sessions:", "");
                String[] split = replace.split("_");
                long start = Long.parseLong(split[0]);
                long end = Long.parseLong(split[1]);
                if (time >= start && time <= end) {
                    List<String> range = stringRedisTemplate.opsForList().range(key, 0, 100);
                    BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                    List<String> list = hashOps.multiGet(range);
                    if (list != null) {
                        return list.stream()
                                .map(item -> {
                                    return JSON.parseObject(item, SeckillSkuRedisTo.class);
                                }).collect(Collectors.toList());
                    }
                    break;
                }
            }
        } catch (BlockException e) {
            log.warn("資源被限流：" + e.getMessage());
        }
        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d-" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo to = JSON.parseObject(json, SeckillSkuRedisTo.class);
                    long current = new Date().getTime();

                    if (current <= to.getStartTime() || current >= to.getEndTime()) {
                        to.setRandomCode(null);
                    }
                    return to;
                }
            }
        }
        return null;
    }

    @Override
    public String kill(String killId, String key, Integer num) {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.threadLocal.get();

        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        String json = hashOps.get(killId);
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            SeckillSkuRedisTo redisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
            long time = new Date().getTime();
            if (time >= redisTo.getStartTime() && time <= redisTo.getEndTime()) {
                String randomCode = redisTo.getRandomCode();
                String skuId = redisTo.getPromotionSessionId() + "-" + redisTo.getSkuId();

                if (randomCode.equals(key) && killId.equals(skuId)) {
                    BigDecimal limit = redisTo.getSeckillLimit();
                    if (num <= limit.intValue()) {
                        String redisKey = memberResponseVo.getId() + "-" + skuId;
                        long ttl = redisTo.getEndTime() - redisTo.getStartTime();

                        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl < 0 ? 0 : ttl, TimeUnit.MILLISECONDS);
                        if (aBoolean) {
                            RSemaphore semaphore = redissonClient.getSemaphore(SKUSTOCK_SEMAPHONE + randomCode);
                            boolean acquire = semaphore.tryAcquire(num);
                            if (acquire) {
                                String orderSn = IdWorker.getTimeId() + UUID.randomUUID().toString().replace("-", "").substring(7, 8);
                                SecKillOrderTo orderTo = new SecKillOrderTo();
                                orderTo.setOrderSn(orderSn);
                                orderTo.setMemberId(memberResponseVo.getId());
                                orderTo.setNum(num);
                                orderTo.setSkuId(redisTo.getSkuId());
                                orderTo.setSeckillPrice(redisTo.getSeckillPrice());
                                orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", orderTo);
                                return orderSn;
                            }
                        } else {
                            return null;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }


    private void saveSessionInfo(List<SeckillSessionsWithSkus> sessions) {
        if (sessions != null) {
            sessions.forEach(session -> {
                long startTime = session.getStartTime().getTime();

                long endTime = session.getEndTime().getTime();
                String key = SESSION_CACHE_PREFIX + startTime + "_" + endTime;
                Boolean hasKey = stringRedisTemplate.hasKey(key);
                if (!hasKey) {
                    List<String> collect = session.getRelationSkus().stream()
                            .map(item -> item.getPromotionSessionId() + "-" + item.getSkuId())
                            .collect(Collectors.toList());
                    stringRedisTemplate.opsForList().leftPushAll(key, collect);
                }
            });
        }
    }

    private void saveSessionSkuInfo(List<SeckillSessionsWithSkus> sessions) {
        if (sessions != null) {
            sessions.forEach(session -> {
                BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                session.getRelationSkus().forEach(seckillSkuVo -> {
                    String randomCode = UUID.randomUUID().toString().replace("-", "");
                    if (!ops.hasKey(seckillSkuVo.getPromotionSessionId() + "-" + seckillSkuVo.getSkuId())) {
                        SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                        BeanUtils.copyProperties(seckillSkuVo, redisTo);
                        R info = productFeign.skuInfo(seckillSkuVo.getSkuId());
                        if (info.getCode() == 0) {
                            SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                            });
                            redisTo.setSkuInfoVo(skuInfo);
                        }
                        redisTo.setStartTime(session.getStartTime().getTime());
                        redisTo.setEndTime(session.getEndTime().getTime());

                        redisTo.setRandomCode(randomCode);

                        ops.put(seckillSkuVo.getPromotionSessionId() + "-" + seckillSkuVo.getSkuId(), JSON.toJSONString(redisTo));
                        RSemaphore semaphore = redissonClient.getSemaphore(SKUSTOCK_SEMAPHONE + randomCode);
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount().intValue());
                    }
                });
            });
        }
    }
}

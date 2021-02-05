package tw.yu.shoppingmall.order.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tw.yu.common.enume.OrderStatusEnum;
import tw.yu.common.exception.NotStockException;
import tw.yu.common.to.MemberResponseVo;
import tw.yu.common.to.mq.OrderTo;
import tw.yu.common.to.mq.SecKillOrderTo;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.order.constant.OrderConstant;
import tw.yu.shoppingmall.order.dao.OrderDao;
import tw.yu.shoppingmall.order.entity.InfoEntity;
import tw.yu.shoppingmall.order.entity.OrderEntity;
import tw.yu.shoppingmall.order.feign.CartFeign;
import tw.yu.shoppingmall.order.feign.MemberFeign;
import tw.yu.shoppingmall.order.feign.ProductFeign;
import tw.yu.shoppingmall.order.feign.WareFeign;
import tw.yu.shoppingmall.order.interceptor.LoginUserInterceptor;
import tw.yu.shoppingmall.order.service.InfoService;
import tw.yu.shoppingmall.order.service.OrderService;
import tw.yu.shoppingmall.order.to.OrderCreateTo;
import tw.yu.shoppingmall.order.vo.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private InfoService infoService;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private WareFeign wareFeign;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.createOrder}")
    private String createOrder;

    @Value("${myRabbitmq.MQConfig.ReleaseOtherKey}")
    private String ReleaseOtherKey;

    private final ThreadLocal<OrderSubmitVo> confirmVoThreadLocal = new ThreadLocal<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.threadLocal.get();
        OrderConfirmVo confirmVo = new OrderConfirmVo();

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            List<MemberAddressVo> address;
            try {
                address = memberFeign.getAddress(memberResponseVo.getId());
                confirmVo.setAddress(address);
            } catch (Exception e) {
                log.warn("\n遠程調用會員服務失敗 [會員服務可能未啟動]");
            }
        }, executor);

        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);

            List<OrderItemVo> items = cartFeign.getCurrentUserCartItems();
            confirmVo.setItems(items);
        }, executor).thenRunAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            List<OrderItemVo> items = confirmVo.getItems();

            List<Long> collect = items.stream()
                    .map(OrderItemVo::getSkuId)
                    .collect(Collectors.toList());
            R hasStock = wareFeign.getSkuHasStock(collect);
            List<SkuStockVo> data = hasStock.getData(new TypeReference<List<SkuStockVo>>() {
            });
            if (data != null) {

                Map<Long, Boolean> stocks = data.stream()
                        .collect(Collectors.toMap(SkuStockVo::getSkuId,
                                SkuStockVo::getHasStock));
                confirmVo.setStocks(stocks);
            }
        }, executor);
        Integer integration = memberResponseVo.getIntegration();
        confirmVo.setIntegration(integration);

        String token = UUID.randomUUID().toString()
                .replace("-", "");
        confirmVo.setOrderToken(token);
        stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId(), token, 10, TimeUnit.MINUTES);
        CompletableFuture.allOf(getAddressFuture, cartFuture).get();
        return confirmVo;
    }

    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) throws ParseException {
        confirmVoThreadLocal.set(vo);
        SubmitOrderResponseVo submitVo = new SubmitOrderResponseVo();
        submitVo.setCode(0);
        MemberResponseVo memberResponseVo = LoginUserInterceptor.threadLocal.get();
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        String orderToken = vo.getOrderToken();

        Long result = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId()), orderToken);
        if (result == 0L) {
            submitVo.setCode(1);
        } else {
            OrderCreateTo order = createOrder();
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal voPayPrice = vo.getPayPrice();
            if (Math.abs(payAmount.subtract(voPayPrice).doubleValue()) < 0.01) {
                saveOrder(order);
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());
                List<OrderItemVo> locks = order.getOrderItems()
                        .stream()
                        .map(item -> {
                            OrderItemVo itemVo = new OrderItemVo();
                            itemVo.setSkuId(item.getSkuId());
                            itemVo.setCount(item.getSkuQuantity());
                            itemVo.setTitle(item.getSkuName());
                            return itemVo;
                        }).collect(Collectors.toList());

                lockVo.setLocks(locks);
                R r = wareFeign.orderLockStock(lockVo);
                if (r.getCode() == 0) {
                    submitVo.setOrderEntity(order.getOrder());
                    rabbitTemplate.convertAndSend(eventExchange, createOrder, order.getOrder());
                } else {
                    String msg = (String) r.get("msg");
                    throw new NotStockException(msg);
                }
            } else {
                submitVo.setCode(2);
            }
        }
        return submitVo;
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        return this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
    }

    @Override
    public void closeOrder(OrderEntity entity) {
        OrderEntity orderEntity = this.getById(entity.getId());
        if (orderEntity.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            OrderEntity update = new OrderEntity();
            update.setId(entity.getId());
            update.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(update);
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderEntity, orderTo);
            try {
                rabbitTemplate.convertAndSend(eventExchange, ReleaseOtherKey, orderTo);
            } catch (AmqpException e) {
            }
        }
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.threadLocal.get();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("member_id", memberResponseVo.getId())
                        .orderByDesc("id")
        );
        List<OrderEntity> order_sn = page.getRecords().stream()
                .peek(order -> {
                    List<InfoEntity> orderSn = infoService.list(new QueryWrapper<InfoEntity>()
                            .eq("order_sn", order.getOrderSn()));
                    order.setItemEntities(orderSn);
                }).collect(Collectors.toList());

        page.setRecords(order_sn);
        return new PageUtils(page);
    }

    @Override
    public String handlePayResult(String orderSn) {
        this.baseMapper.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode());
        return "success";
    }

    @Override
    public void createSecKillOrder(SecKillOrderTo secKillOrderTo) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn(secKillOrderTo.getOrderSn());
        entity.setMemberId(secKillOrderTo.getMemberId());
        entity.setCreateTime(new Date());
        entity.setPayAmount(secKillOrderTo.getSeckillPrice());
        entity.setTotalAmount(secKillOrderTo.getSeckillPrice());
        entity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        entity.setPayType(1);
        BigDecimal price = secKillOrderTo.getSeckillPrice().multiply(new BigDecimal("" + secKillOrderTo.getNum()));
        entity.setPayAmount(price);

        this.save(entity);

        InfoEntity itemEntity = new InfoEntity();
        itemEntity.setOrderSn(secKillOrderTo.getOrderSn());
        itemEntity.setRealAmount(price);
        itemEntity.setOrderId(entity.getId());
        itemEntity.setSkuQuantity(secKillOrderTo.getNum());
        R info = productFeign.getSkuInfoBySkuId(secKillOrderTo.getSkuId());
        SpuInfoVo spuInfo = info.getData(new TypeReference<SpuInfoVo>() {
        });
        itemEntity.setSpuId(spuInfo.getId());
        itemEntity.setSpuBrand(spuInfo.getBrandId().toString());
        itemEntity.setSpuName(spuInfo.getSpuName());
        itemEntity.setCategoryId(spuInfo.getCatalogId());
        itemEntity.setGiftGrowth(secKillOrderTo.getSeckillPrice().multiply(new BigDecimal(secKillOrderTo.getNum())).intValue());
        itemEntity.setGiftIntegration(secKillOrderTo.getSeckillPrice().multiply(new BigDecimal(secKillOrderTo.getNum())).intValue());
        itemEntity.setPromotionAmount(new BigDecimal("0.0"));
        itemEntity.setCouponAmount(new BigDecimal("0.0"));
        itemEntity.setIntegrationAmount(new BigDecimal("0.0"));
        infoService.save(itemEntity);
    }

    private void saveOrder(OrderCreateTo order) throws ParseException {
        OrderEntity orderEntity = order.getOrder();

        orderEntity.setModifyTime(new Date());
        this.save(orderEntity);

        List<InfoEntity> orderItems = order.getOrderItems();
        orderItems = orderItems.stream()
                .peek(item -> {
                    item.setOrderId(orderEntity.getId());
                    item.setSpuName(item.getSpuName());
                    item.setOrderSn(order.getOrder().getOrderSn());
                }).collect(Collectors.toList());
        infoService.saveBatch(orderItems);
    }

    private OrderCreateTo createOrder() throws ParseException {

        OrderCreateTo orderCreateTo = new OrderCreateTo();
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = buildOrderSn(orderSn);

        List<InfoEntity> items = buildOrderItems(orderSn);

        computerPrice(orderEntity, items);
        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(items);
        return orderCreateTo;
    }

    private void computerPrice(OrderEntity orderEntity, List<InfoEntity> items) {
        BigDecimal totalPrice = new BigDecimal("0.0");
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal integration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        BigDecimal gift = new BigDecimal("0.0");
        BigDecimal growth = new BigDecimal("0.0");
        for (InfoEntity item : items) {
            coupon = coupon.add(item.getCouponAmount());
            integration = integration.add(item.getIntegrationAmount());
            promotion = promotion.add(item.getPromotionAmount());
            BigDecimal realAmount = item.getRealAmount();
            totalPrice = totalPrice.add(realAmount);

            gift.add(new BigDecimal(item.getGiftIntegration().toString()));
            growth.add(new BigDecimal(item.getGiftGrowth().toString()));
        }
        orderEntity.setTotalAmount(totalPrice);
        orderEntity.setPayAmount(totalPrice.add(orderEntity.getFreightAmount()));

        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(integration);
        orderEntity.setCouponAmount(coupon);

        orderEntity.setIntegration(gift.intValue());
        orderEntity.setGrowth(growth.intValue());

        orderEntity.setDeleteStatus(OrderStatusEnum.CREATE_NEW.getCode());
    }

    private OrderEntity buildOrderSn(String orderSn) throws ParseException {
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn(orderSn);

        entity.setCreateTime(new Date());
        entity.setCommentTime(new Date());
        entity.setReceiveTime(new Date());
        entity.setDeliveryTime(new Date());
        MemberResponseVo memberResponseVo = LoginUserInterceptor.threadLocal.get();
        entity.setMemberId(memberResponseVo.getId());
        entity.setMemberUsername(memberResponseVo.getUsername());
        entity.setBillReceiverEmail(memberResponseVo.getEmail());

        OrderSubmitVo submitVo = confirmVoThreadLocal.get();
        R fare = wareFeign.getFare(submitVo.getAddrId());
        FareVo response = fare.getData(new TypeReference<FareVo>() {
        });
        entity.setFreightAmount(response.getFare());
        entity.setReceiverCity(response.getMemberAddressVo().getCity());
        entity.setReceiverDetailAddress(response.getMemberAddressVo().getDetailAddress());
        entity.setDeleteStatus(OrderStatusEnum.CREATE_NEW.getCode());
        entity.setReceiverPhone(response.getMemberAddressVo().getPhone());
        entity.setReceiverName(response.getMemberAddressVo().getName());
        entity.setReceiverPostCode(response.getMemberAddressVo().getPostCode());
        entity.setReceiverProvince(response.getMemberAddressVo().getProvince());
        entity.setReceiverRegion(response.getMemberAddressVo().getRegion());

        entity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        entity.setAutoConfirmDay(7);
        return entity;
    }

    private List<InfoEntity> buildOrderItems(String orderSn) {
        List<OrderItemVo> cartItems = cartFeign.getCurrentUserCartItems();
        List<InfoEntity> itemEntities = null;
        if (cartItems != null && cartItems.size() > 0) {
            itemEntities = cartItems.stream()
                    .map(cartItem -> {
                        InfoEntity itemEntity = buildOrderItem(cartItem);
                        itemEntity.setOrderSn(orderSn);
                        return itemEntity;
                    }).collect(Collectors.toList());
        }
        return itemEntities;
    }

    private InfoEntity buildOrderItem(OrderItemVo cartItem) {
        InfoEntity itemEntity = new InfoEntity();

        Long skuId = cartItem.getSkuId();
        R r = productFeign.getSkuInfoBySkuId(skuId);

        SpuInfoVo spuInfo = r.getData(new TypeReference<SpuInfoVo>() {
        });
        itemEntity.setSpuId(spuInfo.getId());
        itemEntity.setSpuBrand(spuInfo.getBrandId().toString());
        itemEntity.setSpuName(spuInfo.getSpuName());
        itemEntity.setCategoryId(spuInfo.getCatalogId());

        itemEntity.setSkuId(cartItem.getSkuId());
        itemEntity.setSkuName(cartItem.getTitle());
        itemEntity.setSkuPic(cartItem.getImage());
        itemEntity.setSkuPrice(cartItem.getPrice());

        String skuAttr = StringUtils.collectionToDelimitedString(cartItem.getSkuAttr(), ";");
        itemEntity.setSkuAttrsVals(skuAttr);
        itemEntity.setSkuQuantity(cartItem.getCount());

        itemEntity.setGiftGrowth(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())).intValue());
        itemEntity.setGiftIntegration(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())).intValue());

        itemEntity.setPromotionAmount(new BigDecimal("0.0"));
        itemEntity.setCouponAmount(new BigDecimal("0.0"));
        itemEntity.setIntegrationAmount(new BigDecimal("0.0"));

        BigDecimal orign = itemEntity.getSkuPrice().multiply(new BigDecimal(itemEntity.getSkuQuantity().toString()));

        BigDecimal subtract = orign.subtract(itemEntity.getCouponAmount())
                .subtract(itemEntity.getPromotionAmount())
                .subtract(itemEntity.getIntegrationAmount());
        itemEntity.setRealAmount(subtract);
        return itemEntity;
    }
}
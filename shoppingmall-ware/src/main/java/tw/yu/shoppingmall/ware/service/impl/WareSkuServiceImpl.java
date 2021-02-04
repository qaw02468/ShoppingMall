package tw.yu.shoppingmall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tw.yu.common.enume.OrderStatusEnum;
import tw.yu.common.exception.NotStockException;
import tw.yu.common.to.SkuHasStockVo;
import tw.yu.common.to.mq.OrderTo;
import tw.yu.common.to.mq.StockDetailTo;
import tw.yu.common.to.mq.StockLockedTo;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.dao.WareSkuDao;
import tw.yu.shoppingmall.ware.entity.WareOrderTaskDetailEntity;
import tw.yu.shoppingmall.ware.entity.WareOrderTaskEntity;
import tw.yu.shoppingmall.ware.entity.WareSkuEntity;
import tw.yu.shoppingmall.ware.feign.OrderFeign;
import tw.yu.shoppingmall.ware.feign.ProductFeign;
import tw.yu.shoppingmall.ware.service.WareOrderTaskDetailService;
import tw.yu.shoppingmall.ware.service.WareOrderTaskService;
import tw.yu.shoppingmall.ware.service.WareSkuService;
import tw.yu.shoppingmall.ware.vo.OrderItemVo;
import tw.yu.shoppingmall.ware.vo.OrderVo;
import tw.yu.shoppingmall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private WareOrderTaskDetailService orderTaskDetailService;

    @Autowired
    private WareOrderTaskService orderTaskService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${myRabbitmq.MQConfig.eventExchange}")
    private String eventExchange;

    @Value("${myRabbitmq.MQConfig.routingKey}")
    private String routingKey;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        List<WareSkuEntity> skuEntities = this.baseMapper.selectList(new QueryWrapper<WareSkuEntity>()
                .eq("sku_id", skuId).eq("ware_id", wareId));
        if (skuEntities != null && skuEntities.size() == 0) {
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setStock(skuNum);
            skuEntity.setWareId(wareId);
            skuEntity.setStockLocked(0);

            try {
                R info = productFeign.info(skuId);

                if (info.getCode() == 0) {
                    Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception ignored) {
            }

        } else {
            this.baseMapper.addStock(skuId, wareId, skuNum);
        }

    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        return skuIds.stream()
                .map(skuId -> {
                    SkuHasStockVo vo = new SkuHasStockVo();

                    Long count = baseMapper.getSkuStock(skuId);
                    vo.setSkuId(skuId);
                    vo.setHasStock(count != null && count > 0);
                    return vo;
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) {
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(vo.getOrderSn());
        orderTaskService.save(taskEntity);

        List<OrderItemVo> locks = vo.getLocks();
        List<SkuWareHasStock> collect = locks.stream()
                .map(item -> {
                    SkuWareHasStock hasStock = new SkuWareHasStock();
                    Long skuId = item.getSkuId();
                    hasStock.setSkuId(skuId);
                    List<Long> wareIds = wareSkuDao.listWareIdHasSkuStock(skuId);
                    hasStock.setWareId(wareIds);
                    hasStock.setNum(item.getCount());
                    return hasStock;
                }).collect(Collectors.toList());

        for (SkuWareHasStock hasStock : collect) {
            boolean skuStocked = true;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();
            if (wareIds == null || wareIds.size() == 0) {
                throw new NotStockException(skuId.toString());
            }
            for (Long wareId : wareIds) {
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());
                if (count == 1) {
                    WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity(null, skuId, "",
                            hasStock.getNum(), taskEntity.getId(), wareId, 1);
                    orderTaskDetailService.save(detailEntity);
                    StockLockedTo stockLockedTo = new StockLockedTo();
                    stockLockedTo.setId(taskEntity.getId());
                    StockDetailTo detailTo = new StockDetailTo();
                    BeanUtils.copyProperties(detailEntity, detailTo);
                    stockLockedTo.setDetailTo(detailTo);

                    rabbitTemplate.convertAndSend(eventExchange, routingKey, stockLockedTo);
                    skuStocked = false;
                    break;
                }
            }
            if (skuStocked) {
                throw new NotStockException(skuId.toString());
            }
        }
        return true;
    }

    @Override
    public void unlockStock(StockLockedTo to) {
        Long id = to.getId();
        StockDetailTo detailTo = to.getDetailTo();
        Long detailId = detailTo.getId();

        WareOrderTaskDetailEntity byId = orderTaskDetailService.getById(detailId);
        if (byId != null) {
            WareOrderTaskEntity taskEntity = orderTaskService.getById(id);
            String orderSn = taskEntity.getOrderSn();
            R orderStatus = orderFeign.getOrderStatus(orderSn);
            if (orderStatus.getCode() == 0) {
                OrderVo orderVo = orderStatus.getData(new TypeReference<OrderVo>() {
                });
                if (orderVo == null || orderVo.getStatus().equals(OrderStatusEnum.CANCLED.getCode())) {
                    if (byId.getLockStatus() == 1) {
                        unLock(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum(), detailId);
                    }
                }
            } else {
                throw new RuntimeException("遠程服務失敗");
            }
        } else {
        }
    }

    @Override
    public void unlockStock(OrderTo to) {
        String orderSn = to.getOrderSn();
        WareOrderTaskEntity taskEntity = orderTaskService.getOrderTaskByOrderSn(orderSn);
        Long taskEntityId = taskEntity.getId();
        List<WareOrderTaskDetailEntity> entities = orderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", taskEntityId)
                .eq("lock_status", 1));
        for (WareOrderTaskDetailEntity entity : entities) {
            unLock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum(), entity.getId());
        }
    }

    private void unLock(Long skuId, Long wareId, Integer num, Long taskDeailId) {
        wareSkuDao.unlockStock(skuId, wareId, num);

        WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity();
        detailEntity.setId(taskDeailId);
        detailEntity.setLockStatus(2);
        orderTaskDetailService.updateById(detailEntity);
    }


    @Data
    class SkuWareHasStock {
        private Long skuId;
        private List<Long> wareId;
        private Integer num;
    }
}


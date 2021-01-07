package tw.yu.shoppingmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.yu.common.constant.WareConstant;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.ware.dao.WarePurchaseDao;
import tw.yu.shoppingmall.ware.entity.WarePurchaseDetailEntity;
import tw.yu.shoppingmall.ware.entity.WarePurchaseEntity;
import tw.yu.shoppingmall.ware.service.WarePurchaseDetailService;
import tw.yu.shoppingmall.ware.service.WarePurchaseService;
import tw.yu.shoppingmall.ware.service.WareSkuService;
import tw.yu.shoppingmall.ware.vo.MergeVo;
import tw.yu.shoppingmall.ware.vo.PurchaseDoneVo;
import tw.yu.shoppingmall.ware.vo.PurchaseItemDoneVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("warePurchaseService")
public class WarePurchaseServiceImpl extends ServiceImpl<WarePurchaseDao, WarePurchaseEntity> implements WarePurchaseService {

    @Autowired
    private WarePurchaseDetailService detailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarePurchaseEntity> page = this.page(
                new Query<WarePurchaseEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        IPage<WarePurchaseEntity> page = this.page(
                new Query<WarePurchaseEntity>().getPage(params),
                new QueryWrapper<WarePurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            WarePurchaseEntity purchaseEntity = new WarePurchaseEntity();

            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }


        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<WarePurchaseDetailEntity> collect = items.stream().map(i -> {
            WarePurchaseDetailEntity detailEntity = new WarePurchaseDetailEntity();

            detailEntity.setId(i);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return detailEntity;
        }).collect(Collectors.toList());


        detailService.updateBatchById(collect);

        WarePurchaseEntity purchaseEntity = new WarePurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

    /**
     * @param idList 采购单id
     */
    @Override
    public void received(List<Long> idList) {
        List<WarePurchaseEntity> collect = idList.stream()
                .map(this::getById).filter(item -> {
                    return item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                            item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode();
                })
                .peek(item -> {
                    item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
                    item.setUpdateTime(new Date());
                }).collect(Collectors.toList());

        this.updateBatchById(collect);


        collect.forEach((item) -> {
            List<WarePurchaseDetailEntity> entities = detailService.listDetailByPurchaseId(item.getId());
            List<WarePurchaseDetailEntity> detailEntities = entities.stream().map(entity -> {
                WarePurchaseDetailEntity entity1 = new WarePurchaseDetailEntity();
                entity1.setId(entity.getId());
                entity1.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return entity1;
            }).collect(Collectors.toList());
            detailService.updateBatchById(detailEntities);
        });
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo doneVo) {

        Long id = doneVo.getId();

        boolean flag = true;

        List<PurchaseItemDoneVo> items = doneVo.getItems();

        List<WarePurchaseDetailEntity> updates = new ArrayList<>();
        for (PurchaseItemDoneVo item : items) {
            WarePurchaseDetailEntity detailEntity = new WarePurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
                flag = false;
                detailEntity.setStatus(item.getStatus());
            } else {
                detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());

                WarePurchaseDetailEntity entity = detailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());

            }
            detailEntity.setId(item.getItemId());
            updates.add(detailEntity);
        }

        detailService.updateBatchById(updates);

        WarePurchaseEntity purchaseEntity = new WarePurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

}
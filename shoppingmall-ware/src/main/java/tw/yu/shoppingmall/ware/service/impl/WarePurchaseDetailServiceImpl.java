package tw.yu.shoppingmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.ware.dao.WarePurchaseDetailDao;
import tw.yu.shoppingmall.ware.entity.WarePurchaseDetailEntity;
import tw.yu.shoppingmall.ware.service.WarePurchaseDetailService;

import java.util.List;
import java.util.Map;


@Service("warePurchaseDetailService")
public class WarePurchaseDetailServiceImpl extends ServiceImpl<WarePurchaseDetailDao, WarePurchaseDetailEntity> implements WarePurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WarePurchaseDetailEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(w -> {
                w.eq("purchase_id", key).or().eq("sku_id", key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WarePurchaseDetailEntity> page = this.page(
                new Query<WarePurchaseDetailEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

    @Override
    public List<WarePurchaseDetailEntity> listDetailByPurchaseId(Long id) {
        return this.list(new QueryWrapper<WarePurchaseDetailEntity>()
                .eq("purchase_id", id));
    }

}
package tw.yu.shoppingmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.to.SkuReductionTo;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品滿減信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}


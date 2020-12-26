package tw.yu.shoppingmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.coupon.entity.CouponInfoEntity;

import java.util.Map;

/**
 * 優惠券信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
public interface CouponInfoService extends IService<CouponInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


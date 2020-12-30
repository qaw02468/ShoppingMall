package tw.yu.shoppingmall.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */

@FeignClient("shoppingmall-coupon")
public interface CouponInfoFeign {

    @RequestMapping("/coupon/couponinfo/member/list")
    R memberCoupons();
}

package tw.yu.shoppingmall.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-coupon")
public interface CouponFeign {
    @GetMapping("/coupon/seckillsession/lates3DaySession")
    public R getLate3DaySession();
}

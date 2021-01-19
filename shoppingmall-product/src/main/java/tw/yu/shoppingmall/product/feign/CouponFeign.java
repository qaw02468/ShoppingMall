package tw.yu.shoppingmall.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tw.yu.common.to.SkuReductionTo;
import tw.yu.common.to.SpuBoundTo;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-coupon")
public interface CouponFeign {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}

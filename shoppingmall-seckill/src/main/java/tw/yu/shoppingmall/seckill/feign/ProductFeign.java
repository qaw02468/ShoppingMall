package tw.yu.shoppingmall.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-product")
public interface ProductFeign {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R skuInfo(@PathVariable("skuId") Long skuId);
}

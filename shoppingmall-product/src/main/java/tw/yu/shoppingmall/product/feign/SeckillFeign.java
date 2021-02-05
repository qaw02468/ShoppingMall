package tw.yu.shoppingmall.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient(value = "shoppingmall-seckill")
public interface SeckillFeign {
    @GetMapping("/sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}

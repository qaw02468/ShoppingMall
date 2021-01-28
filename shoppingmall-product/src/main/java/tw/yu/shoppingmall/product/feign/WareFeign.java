package tw.yu.shoppingmall.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tw.yu.common.utils.R;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-ware")
public interface WareFeign {

    @PostMapping("/ware/waresku/hasstock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);
}

package tw.yu.shoppingmall.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tw.yu.common.utils.R;

import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-order")
public interface OrderFeign {

    @PostMapping("/order/order/listWithItem")
    R listWithItem(@RequestBody Map<String, Object> params);
}
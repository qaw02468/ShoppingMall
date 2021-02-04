package tw.yu.shoppingmall.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tw.yu.shoppingmall.order.vo.OrderItemVo;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-cart")
public interface CartFeign {
    @GetMapping("/currentUserCartItems")
    List<OrderItemVo> getCurrentUserCartItems();
}

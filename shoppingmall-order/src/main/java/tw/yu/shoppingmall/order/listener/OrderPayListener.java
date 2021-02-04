package tw.yu.shoppingmall.order.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.yu.shoppingmall.order.service.OrderService;

/**
 * @author - a89010531111@gmail.com
 */
@RestController
public class OrderPayListener {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/payed/notify/{orderSn}")
    public String handlePayed(@PathVariable("orderSn") String orderSn) {
        return orderService.handlePayResult(orderSn);
    }
}

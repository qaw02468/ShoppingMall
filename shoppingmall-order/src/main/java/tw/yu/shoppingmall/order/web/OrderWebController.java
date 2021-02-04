package tw.yu.shoppingmall.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tw.yu.common.exception.NotStockException;
import tw.yu.shoppingmall.order.service.OrderService;
import tw.yu.shoppingmall.order.vo.OrderConfirmVo;
import tw.yu.shoppingmall.order.vo.OrderSubmitVo;
import tw.yu.shoppingmall.order.vo.SubmitOrderResponseVo;

import java.util.concurrent.ExecutionException;

/**
 * @author - a89010531111@gmail.com
 */
@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("orderConfirmData", confirmVo);
        return "confirm";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(submitVo);
            if (responseVo.getCode() == 0) {
                model.addAttribute("submitOrderResp", responseVo);
                return "pay";
            } else {
                String msg = "下單失敗: ";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "訂單信息過期,請刷新在提交";
                        break;
                    case 2:
                        msg += "訂單商品價格发送變化,請確認後再次提交";
                        break;
                    case 3:
                        msg += "商品庫存不足";
                        break;
                }
                redirectAttributes.addFlashAttribute("msg", msg);
                return "redirect:http://order.shoppingmall.com/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NotStockException) {
                String message = e.getMessage();
                redirectAttributes.addFlashAttribute("msg", message);
            }
            return "redirect:http://order.shoppingmall.com/toTrade";
        }
    }
}

package tw.yu.shoppingmall.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.member.feign.OrderFeign;

import java.util.HashMap;

/**
 * @author - a89010531111@gmail.com
 */
@Controller
public class MemberWebController {
    @Autowired
    private OrderFeign orderFeign;

    @GetMapping("/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, Model model) {
        HashMap<String, Object> page = new HashMap<>();
        page.put("page", pageNum);
        R r = orderFeign.listWithItem(page);
        model.addAttribute("orders", r);
        return "orderList";
    }
}

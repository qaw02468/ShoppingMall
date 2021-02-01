package tw.yu.shoppingmall.cart.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tw.yu.shoppingmall.cart.service.CartService;
import tw.yu.shoppingmall.cart.vo.Cart;
import tw.yu.shoppingmall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author - a89010531111@gmail.com
 */
@Slf4j
@Controller
public class CartController {
    private final String PATH = "redirect:http://cart.shoppingmall.com/cart.html";

    @Autowired
    private CartService cartService;


    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);
        return PATH;
    }

    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        cartService.changeItemCount(skuId, num);
        return PATH;
    }

    @GetMapping("checkItem.html")
    public String checkItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("check") Integer check){
        cartService.checkItem(skuId, check);
        return PATH;
    }

    @GetMapping({"/","/cart.html"})
    public String carListPage(Model model) throws ExecutionException, InterruptedException {

        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId, num);
        redirectAttributes.addAttribute("skuId", skuId);

        return "redirect:http://cart.shoppingmall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam(value = "skuId",required = false) Long skuId,
                                       Model model){
        CartItem cartItem = null;

        if(skuId == null){
            model.addAttribute("item", null);
        }else{
            try {
                cartItem = cartService.getCartItem(skuId);
            } catch (NumberFormatException e) {
                log.warn("惡意操作! 頁面傳來非法字符.");
            }
            model.addAttribute("item", cartItem);
        }
        return "success";
    }
}

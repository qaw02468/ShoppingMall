package tw.yu.shoppingmall.cart.service;

import tw.yu.shoppingmall.cart.vo.Cart;
import tw.yu.shoppingmall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @author - a89010531111@gmail.com
 */
public interface CartService {
    public Cart getCart() throws ExecutionException, InterruptedException;

    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItem getCartItem(long skuId);

    void clearCart(String cartKey);

    void checkItem(Long skuId, Integer check);

    void deleteItem(Long skuId);

    void changeItemCount(Long skuId, Integer num);
}

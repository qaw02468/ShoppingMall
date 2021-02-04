package tw.yu.shoppingmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.cart.feign.ProductFeign;
import tw.yu.shoppingmall.cart.interceptor.CartInterceptor;
import tw.yu.shoppingmall.cart.service.CartService;
import tw.yu.shoppingmall.cart.vo.Cart;
import tw.yu.shoppingmall.cart.vo.CartItem;
import tw.yu.shoppingmall.cart.vo.SkuInfoVo;
import tw.yu.shoppingmall.cart.vo.UserInfoTo;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author - a89010531111@gmail.com
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private ThreadPoolExecutor executor;

    private final String CART_PREFIX = "YU:cart:";

    @Override
    public Cart getCart() throws ExecutionException, InterruptedException {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        Cart cart = new Cart();

        String tempCartKey = CART_PREFIX + userInfoTo.getUserKey();
        if (userInfoTo.getUserId() != null) {
            String cartKey = CART_PREFIX + userInfoTo.getUserId();

            List<CartItem> tempItem = getCartItems(tempCartKey);
            if (tempItem != null) {
                log.info("\n[" + userInfoTo.getUsername() + "] 的購物車已合併");
                for (CartItem cartItem : tempItem) {
                    addToCart(cartItem.getSkuId(), cartItem.getCount());
                }
                clearCart(tempCartKey);
            }
            List<CartItem> cartItems = getCartItems(cartKey);
            cart.setItems(cartItems);
        } else {
            cart.setItems(getCartItems(tempCartKey));
        }
        return cart;
    }

    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String res = (String) cartOps.get(skuId.toString());

        if (StringUtils.isEmpty(res)) {
            CartItem cartItem = new CartItem();

            CompletableFuture<Void> getSkuInfo = CompletableFuture.runAsync(() -> {

                R skuInfo = productFeign.skuInfo(skuId);
                SkuInfoVo sku = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });

                cartItem.setCount(num);
                cartItem.setCheck(true);
                cartItem.setImage(sku.getSkuDefaultImg());
                cartItem.setPrice(sku.getPrice());
                cartItem.setTitle(sku.getSkuTitle());
                cartItem.setSkuId(skuId);
            }, executor);

            CompletableFuture<Void> getSkuSaleAttrValues = CompletableFuture.runAsync(() -> {
                List<String> values = productFeign.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttr(values);
            }, executor);
            CompletableFuture.allOf(getSkuInfo, getSkuSaleAttrValues).get();
            cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        } else {
            CartItem cartItem = JSON.parseObject(res, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        }
    }

    @Override
    public CartItem getCartItem(long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String o = (String) cartOps.get(String.valueOf(skuId));
        return JSON.parseObject(o, CartItem.class);


    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartKey = CART_PREFIX;
        if (userInfoTo.getUserId() != null) {
            log.debug("\n用戶 [" + userInfoTo.getUsername() + "] 正在操作購物車");
            cartKey += userInfoTo.getUserId();
        } else {
            log.debug("\n臨時用戶 [" + userInfoTo.getUserKey() + "] 正在操作購物車");
            cartKey += userInfoTo.getUserKey();
        }

        return stringRedisTemplate.boundHashOps(cartKey);
    }

    private List<CartItem> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(cartKey);
        List<Object> values = hashOps.values();
        if (values != null && values.size() > 0) {
            return values.stream()
                    .map(obj -> JSON.parseObject((String) obj, CartItem.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void clearCart(String cartKey) {
        stringRedisTemplate.delete(cartKey);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCheck(check == 1);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }

    @Override
    public void changeItemCount(Long skuId, Integer num) {
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(), JSON.toJSONString(cartItem));
    }

    @Override
    public List<CartItem> getUserCartItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() == null) {
            return null;
        } else {
            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            List<CartItem> cartItems = getCartItems(cartKey);

            List<CartItem> collect = cartItems.stream()
                    .filter(CartItem::getCheck)
                    .peek(item -> {
                        try {
                            R r = productFeign.getPrice(item.getSkuId());
                            String price = (String) r.get("data");
                            item.setPrice(new BigDecimal(price));
                        } catch (Exception e) {
                            log.warn("遠程查詢商品價格出錯 [商品服務未啟動]");
                        }
                    }).collect(Collectors.toList());
            return collect;
        }
    }
}

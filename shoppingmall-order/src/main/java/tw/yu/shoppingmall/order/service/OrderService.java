package tw.yu.shoppingmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.order.entity.OrderEntity;
import tw.yu.shoppingmall.order.vo.OrderConfirmVo;
import tw.yu.shoppingmall.order.vo.OrderSubmitVo;
import tw.yu.shoppingmall.order.vo.SubmitOrderResponseVo;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 訂單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) throws ParseException;

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity entity);

    PageUtils queryPageWithItem(Map<String, Object> params);

    String handlePayResult(String orderSn);
}


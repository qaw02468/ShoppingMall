package tw.yu.shoppingmall.order.vo;

import lombok.Data;
import tw.yu.shoppingmall.order.entity.OrderEntity;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SubmitOrderResponseVo {
    private OrderEntity orderEntity;
    private Integer code;
}
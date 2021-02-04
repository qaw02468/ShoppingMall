package tw.yu.shoppingmall.order.to;

import lombok.Data;
import tw.yu.shoppingmall.order.entity.InfoEntity;
import tw.yu.shoppingmall.order.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class OrderCreateTo {
    private OrderEntity order;
    private List<InfoEntity> orderItems;
    private BigDecimal payPrice;
    private BigDecimal fare;
}
package tw.yu.shoppingmall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class OrderSubmitVo {
    private Long addrId;
    private Integer payType;
    private String orderToken;
    private BigDecimal payPrice;
    private String note;
}
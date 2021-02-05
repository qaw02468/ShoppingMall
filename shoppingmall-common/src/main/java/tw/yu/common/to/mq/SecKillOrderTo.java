package tw.yu.common.to.mq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SecKillOrderTo {
    private String orderSn;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private Integer num;
    private Long memberId;
}
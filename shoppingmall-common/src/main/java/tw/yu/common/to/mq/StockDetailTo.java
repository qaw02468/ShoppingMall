package tw.yu.common.to.mq;

import lombok.Data;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class StockDetailTo {
    private Long id;
    private Long skuId;
    private String skuName;
    private Integer skuNum;
    private Long taskId;
    private Long wareId;
    private Integer lockStatus;
}
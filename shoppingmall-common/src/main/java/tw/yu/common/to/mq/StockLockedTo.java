package tw.yu.common.to.mq;

import lombok.Data;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class StockLockedTo {

    /**
     * 库存工作单id
     */
    private Long id;

    /**
     * 工作详情id
     */
    private StockDetailTo detailTo;
}
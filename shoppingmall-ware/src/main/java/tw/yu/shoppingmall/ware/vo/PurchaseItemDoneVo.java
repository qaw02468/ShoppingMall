package tw.yu.shoppingmall.ware.vo;

import lombok.Data;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;

}

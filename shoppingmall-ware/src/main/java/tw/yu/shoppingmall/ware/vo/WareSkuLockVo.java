package tw.yu.shoppingmall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class WareSkuLockVo {
    private String orderSn;
    private List<OrderItemVo> locks;

}
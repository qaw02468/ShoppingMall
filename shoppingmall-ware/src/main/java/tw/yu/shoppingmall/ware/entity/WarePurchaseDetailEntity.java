package tw.yu.shoppingmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:01
 */
@Data
@TableName("shoppingmall_ware_manage.ware_purchase_detail")
public class WarePurchaseDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 採購單id
     */
    private Long purchaseId;
    /**
     * 採購商品id
     */
    private Long skuId;
    /**
     * 採購數量
     */
    private Integer skuNum;
    /**
     * 採購金額
     */
    private BigDecimal skuPrice;
    /**
     * 倉庫id
     */
    private Long wareId;
    /**
     * 狀態[0新建，1已分配，2正在採購，3已完成，4採購失敗]
     */
    private Integer status;

}

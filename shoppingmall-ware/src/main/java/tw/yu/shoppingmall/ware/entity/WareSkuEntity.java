package tw.yu.shoppingmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品庫存
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
@Data
@TableName("shoppingmall_ware_manage.ware_sku")
public class WareSkuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * 倉庫id
     */
    private Long wareId;
    /**
     * 庫存數
     */
    private Integer stock;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 鎖定庫存
     */
    private Integer stockLocked;

}

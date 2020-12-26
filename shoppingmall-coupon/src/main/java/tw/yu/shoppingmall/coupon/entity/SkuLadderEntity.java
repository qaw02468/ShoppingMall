package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品階梯價格
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_sku_ladder")
public class SkuLadderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * spu_id
     */
    private Long skuId;
    /**
     * 滿幾件
     */
    private Integer fullCount;
    /**
     * 打幾折
     */
    private BigDecimal discount;
    /**
     * 折後價
     */
    private BigDecimal price;
    /**
     * 是否疊加其他優惠[0-不可疊加，1-可疊加]
     */
    private Integer addOther;

}

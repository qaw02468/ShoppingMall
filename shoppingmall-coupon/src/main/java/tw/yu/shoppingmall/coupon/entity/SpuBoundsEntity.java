package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品spu積分設置
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_spu_bounds")
public class SpuBoundsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long spuId;
    /**
     * 成長積分
     */
    private BigDecimal growBounds;
    /**
     * 購物積分
     */
    private BigDecimal buyBounds;
    /**
     * 優惠生效情況[1111（四個狀態位，從右到左）;0 -無優惠，成長積分是否贈送;1 -無優惠，購物積分是否贈送;2 -有優惠，成長積分是否贈送;3 -有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]
     */
    private Integer work;

}

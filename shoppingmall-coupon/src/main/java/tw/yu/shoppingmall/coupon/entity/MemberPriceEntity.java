package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品會員價格
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_member_price")
public class MemberPriceEntity implements Serializable {
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
     * 會員等級id
     */
    private Long memberLevelId;
    /**
     * 會員等級名
     */
    private String memberLevelName;
    /**
     * 會員對應價格
     */
    private BigDecimal memberPrice;
    /**
     * 可否疊加其他優惠[0-不可疊加優惠，1-可疊加]
     */
    private Integer addOther;

}

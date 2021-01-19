package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * sku銷售屬性&值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_sku_sale_attributes_value")
public class SkuSaleAttributesValueEntity implements Serializable {
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
     * attr_id
     */
    private Long attrId;
    /**
     * 銷售屬性名
     */
    private String attrName;
    /**
     * 銷售屬性值
     */
    private String attrValue;
    /**
     * 順序
     */
    private Integer attrSort;

}

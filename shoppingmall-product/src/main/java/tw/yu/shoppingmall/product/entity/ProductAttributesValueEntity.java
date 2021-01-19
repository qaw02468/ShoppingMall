package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu屬性值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_product_attributes_value")
public class ProductAttributesValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 屬性id
     */
    private Long attrId;
    /**
     * 屬性名
     */
    private String attrName;
    /**
     * 屬性值
     */
    private String attrValue;
    /**
     * 順序
     */
    private Integer attrSort;
    /**
     * 快速展示【是否展示在介紹上；0-否1-是】
     */
    private Integer quickShow;

}

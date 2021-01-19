package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_sku_info")
public class SkuInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * skuId
     */
    @TableId
    private Long skuId;
    /**
     * spuId
     */
    private Long spuId;
    /**
     * sku名稱
     */
    private String skuName;
    /**
     * sku介紹描述
     */
    private String skuDesc;
    /**
     * 所屬分類id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 默認圖片
     */
    private String skuDefaultImg;
    /**
     * 標題
     */
    private String skuTitle;
    /**
     * 副標題
     */
    private String skuSubtitle;
    /**
     * 價格
     */
    private BigDecimal price;
    /**
     * 銷量
     */
    private Long saleCount;

}

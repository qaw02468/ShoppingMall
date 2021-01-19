package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * sku圖片
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_sku_images")
public class SkuImagesEntity implements Serializable {
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
     * 圖片地址
     */
    private String imgUrl;
    /**
     * 排序
     */
    private Integer imgSort;
    /**
     * 默認圖[0 -不是默認圖，1 -是默認圖]
     */
    private Integer defaultImg;

}

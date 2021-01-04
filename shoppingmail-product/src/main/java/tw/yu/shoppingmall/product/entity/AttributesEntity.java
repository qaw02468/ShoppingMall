package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品屬性
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_attributes")
public class AttributesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 屬性id
     */
    @TableId
    private Long attrId;
    /**
     * 屬性名
     */
    private String attrName;
    /**
     * 是否需要檢索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 屬性圖標
     */
    private String icon;
    /**
     * 可選值列表[用逗號分隔]
     */
    private String valueSelect;
    /**
     * 屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]
     */
    private Integer attrType;
    /**
     * 啟用狀態[0 -禁用，1 -啟用]
     */
    private Long enable;
    /**
     * 所屬分類
     */
    private Long catelogId;
    /**
     * 快速展示【是否展示在介紹上；0-否1-是】，在sku中仍然可以調整
     */
    private Integer showDesc;


}

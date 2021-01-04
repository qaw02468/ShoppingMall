package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品三級分類
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Data
@TableName("product_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分類id
     */
    @TableId
    private Long catId;
    /**
     * 分類名稱
     */
    private String name;
    /**
     * 父分類id
     */
    private Long parentCid;
    /**
     * 層級
     */
    private Integer catLevel;
    /**
     * 是否顯示[0-不顯示，1顯示]
     */
    @TableLogic(value = "1", delval = "0")
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 圖標地址
     */
    private String icon;
    /**
     * 計量單位
     */
    private String productUnit;
    /**
     * 商品數量
     */
    private Integer productCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<CategoryEntity> children;

}

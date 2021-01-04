package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import tw.yu.common.vaild.AddGroup;
import tw.yu.common.vaild.ListValue;
import tw.yu.common.vaild.UpdateGroup;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Data
@TableName("product_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(message = "修改必須指定品牌ID", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定ID", groups = {AddGroup.class})
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名必須提交", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotEmpty(groups = {AddGroup.class})
    @URL(message = "logo必須是合法url地址", groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 介紹
     */
    private String descript;
    /**
     * 顯示狀態[0-不顯示；1-顯示]
     */
    @NotNull(groups = {AddGroup.class,UpdateGroup.class})
    @ListValue(value = {0, 1}, groups = {AddGroup.class,UpdateGroup.class})
    private Integer showStatus;
    /**
     * 檢索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "首字母必須是一個字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, message = ("排序必須大於等於0"), groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}

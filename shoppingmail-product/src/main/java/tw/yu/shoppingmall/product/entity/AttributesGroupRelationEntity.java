package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 屬性&屬性分組關聯
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Data
@TableName("product_attributes_group_relation")
public class AttributesGroupRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 屬性id
     */
    private Long attrId;
    /**
     * 屬性分組id
     */
    private Long attrGroupId;
    /**
     * 屬性組內排序
     */
    private Integer attrSort;

}

package tw.yu.shoppingmall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import tw.yu.shoppingmall.product.entity.AttributesEntity;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class AttrGroupWithAttrVo {
    /**
     * 分組id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 組名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 組圖標
     */
    private String icon;
    /**
     * 所屬分類id
     */
    private Long catelogId;

    private List<AttributesEntity> attrs;
}

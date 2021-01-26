package tw.yu.shoppingmall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}

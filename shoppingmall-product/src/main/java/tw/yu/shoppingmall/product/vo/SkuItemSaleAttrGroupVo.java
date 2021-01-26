package tw.yu.shoppingmall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SkuItemSaleAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}

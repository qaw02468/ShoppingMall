package tw.yu.shoppingmall.product.vo;

import lombok.Data;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class AttributesResponseVo extends AttributesVo {
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}

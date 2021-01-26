package tw.yu.shoppingmall.product.vo;

import lombok.Data;
import tw.yu.shoppingmall.product.entity.SkuImagesEntity;
import tw.yu.shoppingmall.product.entity.SkuInfoEntity;
import tw.yu.shoppingmall.product.entity.SpuInfoDescEntity;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SkuItemVo {
    private SkuInfoEntity info;
    private List<SkuImagesEntity> images;
    private List<SkuItemSaleAttrVo> saleAttr;
    private SpuInfoDescEntity desc;
    private List<SkuItemSaleAttrGroupVo> groupAttrs;
    private boolean hasStock = true;
}

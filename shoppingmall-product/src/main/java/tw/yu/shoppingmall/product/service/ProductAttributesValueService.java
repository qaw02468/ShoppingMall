package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.ProductAttributesValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu屬性值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
public interface ProductAttributesValueService extends IService<ProductAttributesValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttributesValueEntity> collect);

    List<ProductAttributesValueEntity> baseAttrlistForSpu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttributesValueEntity> entities);
}


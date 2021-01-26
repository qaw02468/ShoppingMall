package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.vo.AttrGroupWithAttrVo;
import tw.yu.shoppingmall.product.vo.SkuItemSaleAttrGroupVo;
import tw.yu.shoppingmall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;

/**
 * 屬性分組
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
public interface AttributesGroupService extends IService<AttributesGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long cateLogId);

    List<AttrGroupWithAttrVo> getAttributeGroupWithAttributeByCateLogId(Long cateLogId);

    List<SkuItemSaleAttrGroupVo> getAttributeGroupWithAttributeBySpuId(Long spuId, Long catalogId);
}


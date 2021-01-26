package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.vo.SkuItemSaleAttrGroupVo;

import java.util.List;

/**
 * 屬性分組
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Mapper
public interface AttributesGroupDao extends BaseMapper<AttributesGroupEntity> {

    List<SkuItemSaleAttrGroupVo> getAttributeGroupWithAttributeBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}

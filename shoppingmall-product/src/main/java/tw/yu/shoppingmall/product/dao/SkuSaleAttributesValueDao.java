package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.yu.shoppingmall.product.entity.SkuSaleAttributesValueEntity;
import tw.yu.shoppingmall.product.vo.SkuItemSaleAttrVo;

import java.util.List;

/**
 * sku銷售屬性&值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Mapper
public interface SkuSaleAttributesValueDao extends BaseMapper<SkuSaleAttributesValueEntity> {

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);
}

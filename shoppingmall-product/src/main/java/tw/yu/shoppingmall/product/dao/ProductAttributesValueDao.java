package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.product.entity.ProductAttributesValueEntity;

/**
 * spu屬性值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Mapper
public interface ProductAttributesValueDao extends BaseMapper<ProductAttributesValueEntity> {

}

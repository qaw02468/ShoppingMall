package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.product.entity.CategoryEntity;

/**
 * 商品三級分類
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}

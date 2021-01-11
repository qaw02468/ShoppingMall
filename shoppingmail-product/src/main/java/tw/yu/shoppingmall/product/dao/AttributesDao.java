package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.yu.shoppingmall.product.entity.AttributesEntity;

import java.util.List;

/**
 * 商品屬性
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Mapper
public interface AttributesDao extends BaseMapper<AttributesEntity> {

    List<Long> selectSearchAttrIdList(@Param("attrIdList") List<Long> attrIdList);
}

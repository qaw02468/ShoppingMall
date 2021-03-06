package tw.yu.shoppingmall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.yu.shoppingmall.product.entity.AttributesGroupRelationEntity;
import tw.yu.shoppingmall.product.vo.AttributesGroupRelationVo;

import java.util.List;

/**
 * 屬性&屬性分組關聯
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Mapper
public interface AttributesGroupRelationDao extends BaseMapper<AttributesGroupRelationEntity> {

    void deleteBatchRelation(@Param("entities") List<AttributesGroupRelationVo> entities);
}

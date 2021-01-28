package tw.yu.shoppingmall.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.member.entity.MemberLevelEntity;

/**
 * 會員等級
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

    MemberLevelEntity getDefaultLevel();
}

package tw.yu.shoppingmall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.ware.entity.OrderTaskDetailEntity;

/**
 * 庫存工作單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
@Mapper
public interface OrderTaskDetailDao extends BaseMapper<OrderTaskDetailEntity> {

}

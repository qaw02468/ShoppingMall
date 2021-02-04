package tw.yu.shoppingmall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.yu.shoppingmall.order.entity.OrderEntity;

/**
 * 訂單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void save(@Param("orderEntity") OrderEntity orderEntity);

    void updateOrderStatus(@Param("orderSn") String orderSn, @Param("code") Integer code);
}

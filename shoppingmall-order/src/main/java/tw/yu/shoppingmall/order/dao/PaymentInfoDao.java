package tw.yu.shoppingmall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.order.entity.PaymentInfoEntity;

/**
 * 支付信息表
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {

}

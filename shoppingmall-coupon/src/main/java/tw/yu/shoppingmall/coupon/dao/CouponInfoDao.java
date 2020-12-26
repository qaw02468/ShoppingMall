package tw.yu.shoppingmall.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tw.yu.shoppingmall.coupon.entity.CouponInfoEntity;

/**
 * 優惠券信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Mapper
public interface CouponInfoDao extends BaseMapper<CouponInfoEntity> {

}

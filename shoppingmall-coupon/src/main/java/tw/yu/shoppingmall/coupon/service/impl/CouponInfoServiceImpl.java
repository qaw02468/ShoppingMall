package tw.yu.shoppingmall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.coupon.dao.CouponInfoDao;
import tw.yu.shoppingmall.coupon.entity.CouponInfoEntity;
import tw.yu.shoppingmall.coupon.service.CouponInfoService;

import java.util.Map;


@Service("couponInfoService")
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoDao, CouponInfoEntity> implements CouponInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponInfoEntity> page = this.page(
                new Query<CouponInfoEntity>().getPage(params),
                new QueryWrapper<CouponInfoEntity>()
        );

        return new PageUtils(page);
    }

}
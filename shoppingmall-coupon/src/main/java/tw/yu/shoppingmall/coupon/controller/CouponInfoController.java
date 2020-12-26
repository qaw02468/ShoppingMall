package tw.yu.shoppingmall.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.coupon.entity.CouponInfoEntity;
import tw.yu.shoppingmall.coupon.service.CouponInfoService;

import java.util.Arrays;
import java.util.Map;


/**
 * 優惠券信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@RestController
@RequestMapping("coupon/couponinfo")
public class CouponInfoController {
    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CouponInfoEntity couponInfo = couponInfoService.getById(id);

        return R.ok().put("couponInfo", couponInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponInfoEntity couponInfo) {
        couponInfoService.save(couponInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponInfoEntity couponInfo) {
        couponInfoService.updateById(couponInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        couponInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

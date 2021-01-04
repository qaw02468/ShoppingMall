package tw.yu.shoppingmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.common.vaild.AddGroup;
import tw.yu.common.vaild.UpdateGroup;
import tw.yu.common.vaild.UpdateStatusGroup;
import tw.yu.shoppingmall.product.entity.BrandEntity;
import tw.yu.shoppingmall.product.service.BrandService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Valid
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand) {
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class})@RequestBody BrandEntity brand) {
        brandService.updateByDetail(brand);

        return R.ok();
    }

    @RequestMapping("/update/status")
    public R updateStatus(@Validated({UpdateStatusGroup.class})@RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}

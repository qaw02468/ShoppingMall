package tw.yu.shoppingmall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.entity.SkuEntity;
import tw.yu.shoppingmall.ware.service.SkuService;

import java.util.Arrays;
import java.util.Map;


/**
 * 商品庫存
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
@RestController
@RequestMapping("ware/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SkuEntity sku = skuService.getById(id);

        return R.ok().put("sku", sku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuEntity sku) {
        skuService.save(sku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuEntity sku) {
        skuService.updateById(sku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        skuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

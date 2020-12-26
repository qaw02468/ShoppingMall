package tw.yu.shoppingmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.AttributesEntity;
import tw.yu.shoppingmall.product.service.AttributesService;

import java.util.Arrays;
import java.util.Map;


/**
 * 商品屬性
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@RestController
@RequestMapping("product/attributes")
public class AttributesController {
    @Autowired
    private AttributesService attributesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attributesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttributesEntity attributes = attributesService.getById(attrId);

        return R.ok().put("attributes", attributes);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttributesEntity attributes) {
        attributesService.save(attributes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributesEntity attributes) {
        attributesService.updateById(attributes);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attributesService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}

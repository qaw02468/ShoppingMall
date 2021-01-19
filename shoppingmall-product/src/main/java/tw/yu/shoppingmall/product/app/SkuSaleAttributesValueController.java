package tw.yu.shoppingmall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.SkuSaleAttributesValueEntity;
import tw.yu.shoppingmall.product.service.SkuSaleAttributesValueService;

import java.util.Arrays;
import java.util.Map;


/**
 * sku銷售屬性&值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@RestController
@RequestMapping("product/skusaleattributesvalue")
public class SkuSaleAttributesValueController {
    @Autowired
    private SkuSaleAttributesValueService skuSaleAttributesValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuSaleAttributesValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SkuSaleAttributesValueEntity skuSaleAttributesValue = skuSaleAttributesValueService.getById(id);

        return R.ok().put("skuSaleAttributesValue", skuSaleAttributesValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuSaleAttributesValueEntity skuSaleAttributesValue) {
        skuSaleAttributesValueService.save(skuSaleAttributesValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuSaleAttributesValueEntity skuSaleAttributesValue) {
        skuSaleAttributesValueService.updateById(skuSaleAttributesValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        skuSaleAttributesValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

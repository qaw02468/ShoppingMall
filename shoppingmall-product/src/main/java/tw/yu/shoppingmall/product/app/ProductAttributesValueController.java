package tw.yu.shoppingmall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.ProductAttributesValueEntity;
import tw.yu.shoppingmall.product.service.ProductAttributesValueService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * spu屬性值
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@RestController
@RequestMapping("product/productattributesvalue")
public class ProductAttributesValueController {
    @Autowired
    private ProductAttributesValueService productAttributesValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = productAttributesValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ProductAttributesValueEntity productAttributesValue = productAttributesValueService.getById(id);

        return R.ok().put("productAttributesValue", productAttributesValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProductAttributesValueEntity productAttributesValue) {
        productAttributesValueService.save(productAttributesValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProductAttributesValueEntity productAttributesValue) {
        productAttributesValueService.updateById(productAttributesValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        productAttributesValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

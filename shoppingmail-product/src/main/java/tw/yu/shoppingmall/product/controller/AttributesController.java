package tw.yu.shoppingmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.ProductAttributesValueEntity;
import tw.yu.shoppingmall.product.service.AttributesService;
import tw.yu.shoppingmall.product.service.ProductAttributesValueService;
import tw.yu.shoppingmall.product.vo.AttributesResponseVo;
import tw.yu.shoppingmall.product.vo.AttributesVo;

import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private ProductAttributesValueService productAttributesValueService;

    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId) {

        List<ProductAttributesValueEntity> entities = productAttributesValueService.baseAttrlistForSpu(spuId);

        return R.ok().put("data", entities);
    }

    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttributesValueEntity> entities) {
        productAttributesValueService.updateSpuAttr(spuId, entities);
        return R.ok();
    }

    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttributesList(@RequestParam Map<String, Object> params,
                                @PathVariable("catelogId") Long cateLogId,
                                @PathVariable("attrType") String type) {
        PageUtils page = attributesService.queryBaseAttributesPage(params, cateLogId, type);

        return R.ok().put("page", page);
    }

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
        AttributesResponseVo attributesResponseVo = attributesService.getAttributesInfo(attrId);


        return R.ok().put("attr", attributesResponseVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttributesVo attributes) {
        attributesService.saveAttributes(attributes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributesVo attributes) {
        attributesService.updateAttr(attributes);

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

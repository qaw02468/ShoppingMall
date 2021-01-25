package tw.yu.shoppingmall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.AttributesGroupRelationEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupRelationService;

import java.util.Arrays;
import java.util.Map;


/**
 * 屬性&屬性分組關聯
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@RestController
@RequestMapping("product/attributesgrouprelation")
public class AttributesGroupRelationController {
    @Autowired
    private AttributesGroupRelationService attributesGroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attributesGroupRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AttributesGroupRelationEntity attributesGroupRelation = attributesGroupRelationService.getById(id);

        return R.ok().put("attributesGroupRelation", attributesGroupRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttributesGroupRelationEntity attributesGroupRelation) {
        attributesGroupRelationService.save(attributesGroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributesGroupRelationEntity attributesGroupRelation) {
        attributesGroupRelationService.updateById(attributesGroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        attributesGroupRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

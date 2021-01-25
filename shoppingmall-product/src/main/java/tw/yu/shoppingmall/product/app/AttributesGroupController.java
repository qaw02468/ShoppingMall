package tw.yu.shoppingmall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.AttributesEntity;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupRelationService;
import tw.yu.shoppingmall.product.service.AttributesGroupService;
import tw.yu.shoppingmall.product.service.AttributesService;
import tw.yu.shoppingmall.product.service.CategoryService;
import tw.yu.shoppingmall.product.vo.AttrGroupWithAttrVo;
import tw.yu.shoppingmall.product.vo.AttributesGroupRelationVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 屬性分組
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@RestController
@RequestMapping("product/attributesgroup")
public class AttributesGroupController {

    @Autowired
    private AttributesGroupService attributesGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttributesService attributeServices;

    @Autowired
    private AttributesGroupRelationService attributeRelationService;

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrGroupId) {
        List<AttributesEntity> attributesEntityList = attributeServices.getRelationAttr(attrGroupId);

        return R.ok().put("data", attributesEntityList);
    }

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttributesGroupRelationVo> vos) {

        attributeRelationService.saveBatch(vos);

        return R.ok();
    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrGroupId,
                            @RequestParam Map<String, Object> params) {
        PageUtils page = attributeServices.getNoRelationAttr(params, attrGroupId);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{cateLogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("cateLogId") Long cateLogId) {

        PageUtils page = attributesGroupService.queryPage(params, cateLogId);
        return R.ok().put("page", page);
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttributeGroupWithAttribute(@PathVariable("catelogId") Long cateLogId) {

        List<AttrGroupWithAttrVo> vos = attributesGroupService.getAttributeGroupWithAttributeByCateLogId(cateLogId);

        return R.ok().put("data", vos);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttributesGroupEntity attributesGroup = attributesGroupService.getById(attrGroupId);

        Long cateLogId = attributesGroup.getCatelogId();
        Long[] cateLogPath = categoryService.findCateLogPath(cateLogId);

        attributesGroup.setCateLogPath(cateLogPath);

        return R.ok().put("attributesGroup", attributesGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttributesGroupEntity attributesGroup) {
        attributesGroupService.save(attributesGroup);

        return R.ok();
    }


    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttributesGroupRelationVo[] vos) {
        attributeServices.deleteRelation(vos);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttributesGroupEntity attributesGroup) {
        attributesGroupService.updateById(attributesGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attributesGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}

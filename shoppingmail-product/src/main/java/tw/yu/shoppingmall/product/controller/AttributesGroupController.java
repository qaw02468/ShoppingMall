package tw.yu.shoppingmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupService;

import java.util.Arrays;
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

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attributesGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttributesGroupEntity attributesGroup = attributesGroupService.getById(attrGroupId);

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

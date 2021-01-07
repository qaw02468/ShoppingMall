package tw.yu.shoppingmall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.entity.WarePurchaseDetailEntity;
import tw.yu.shoppingmall.ware.service.WarePurchaseDetailService;

import java.util.Arrays;
import java.util.Map;


/**
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:01
 */
@RestController
@RequestMapping("ware/warepurchasedetail")
public class WarePurchaseDetailController {
    @Autowired
    private WarePurchaseDetailService warePurchaseDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = warePurchaseDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WarePurchaseDetailEntity warePurchaseDetail = warePurchaseDetailService.getById(id);

        return R.ok().put("warePurchaseDetail", warePurchaseDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WarePurchaseDetailEntity warePurchaseDetail) {
        warePurchaseDetailService.save(warePurchaseDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WarePurchaseDetailEntity warePurchaseDetail) {
        warePurchaseDetailService.updateById(warePurchaseDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        warePurchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

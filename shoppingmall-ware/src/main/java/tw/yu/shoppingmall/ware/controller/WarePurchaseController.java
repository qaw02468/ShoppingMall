package tw.yu.shoppingmall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.entity.WarePurchaseEntity;
import tw.yu.shoppingmall.ware.service.WarePurchaseService;
import tw.yu.shoppingmall.ware.vo.MergeVo;
import tw.yu.shoppingmall.ware.vo.PurchaseDoneVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 採購信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
@RestController
@RequestMapping("ware/warepurchase")
public class WarePurchaseController {
    @Autowired
    private WarePurchaseService purchaseService;


    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);
        return R.ok();
    }

    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    @PostMapping("/received")
    public R received(@RequestBody List<Long> idList) {
        purchaseService.received(idList);

        return R.ok();
    }

    @RequestMapping("/unreceive/list")
    public R unreceivelist(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPageUnreceive(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        WarePurchaseEntity warePurchase = purchaseService.getById(id);

        return R.ok().put("warePurchase", warePurchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WarePurchaseEntity warePurchase) {
        purchaseService.save(warePurchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WarePurchaseEntity warePurchase) {
        purchaseService.updateById(warePurchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

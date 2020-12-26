package tw.yu.shoppingmall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.order.entity.ReturnReasonEntity;
import tw.yu.shoppingmall.order.service.ReturnReasonService;

import java.util.Arrays;
import java.util.Map;


/**
 * 退貨原因
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@RestController
@RequestMapping("order/returnreason")
public class ReturnReasonController {
    @Autowired
    private ReturnReasonService returnReasonService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = returnReasonService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ReturnReasonEntity returnReason = returnReasonService.getById(id);

        return R.ok().put("returnReason", returnReason);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReturnReasonEntity returnReason) {
        returnReasonService.save(returnReason);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReturnReasonEntity returnReason) {
        returnReasonService.updateById(returnReason);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        returnReasonService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

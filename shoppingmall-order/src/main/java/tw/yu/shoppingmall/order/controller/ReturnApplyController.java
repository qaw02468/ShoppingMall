package tw.yu.shoppingmall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.order.entity.ReturnApplyEntity;
import tw.yu.shoppingmall.order.service.ReturnApplyService;

import java.util.Arrays;
import java.util.Map;


/**
 * 訂單退貨申請
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@RestController
@RequestMapping("order/returnapply")
public class ReturnApplyController {
    @Autowired
    private ReturnApplyService returnApplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = returnApplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ReturnApplyEntity returnApply = returnApplyService.getById(id);

        return R.ok().put("returnApply", returnApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReturnApplyEntity returnApply) {
        returnApplyService.save(returnApply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReturnApplyEntity returnApply) {
        returnApplyService.updateById(returnApply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        returnApplyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

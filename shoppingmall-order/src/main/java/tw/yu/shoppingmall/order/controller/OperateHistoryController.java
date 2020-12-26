package tw.yu.shoppingmall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.order.entity.OperateHistoryEntity;
import tw.yu.shoppingmall.order.service.OperateHistoryService;

import java.util.Arrays;
import java.util.Map;


/**
 * 訂單操作歷史記錄
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@RestController
@RequestMapping("order/operatehistory")
public class OperateHistoryController {
    @Autowired
    private OperateHistoryService operateHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = operateHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OperateHistoryEntity operateHistory = operateHistoryService.getById(id);

        return R.ok().put("operateHistory", operateHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OperateHistoryEntity operateHistory) {
        operateHistoryService.save(operateHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OperateHistoryEntity operateHistory) {
        operateHistoryService.updateById(operateHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        operateHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

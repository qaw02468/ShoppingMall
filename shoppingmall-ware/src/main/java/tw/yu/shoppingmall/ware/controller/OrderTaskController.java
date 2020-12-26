package tw.yu.shoppingmall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.entity.OrderTaskEntity;
import tw.yu.shoppingmall.ware.service.OrderTaskService;

import java.util.Arrays;
import java.util.Map;


/**
 * 庫存工作單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
@RestController
@RequestMapping("ware/ordertask")
public class OrderTaskController {
    @Autowired
    private OrderTaskService orderTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderTaskEntity orderTask = orderTaskService.getById(id);

        return R.ok().put("orderTask", orderTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderTaskEntity orderTask) {
        orderTaskService.save(orderTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderTaskEntity orderTask) {
        orderTaskService.updateById(orderTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

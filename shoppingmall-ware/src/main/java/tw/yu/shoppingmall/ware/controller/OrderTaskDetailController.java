package tw.yu.shoppingmall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.ware.entity.OrderTaskDetailEntity;
import tw.yu.shoppingmall.ware.service.OrderTaskDetailService;

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
@RequestMapping("ware/ordertaskdetail")
public class OrderTaskDetailController {
    @Autowired
    private OrderTaskDetailService orderTaskDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderTaskDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        OrderTaskDetailEntity orderTaskDetail = orderTaskDetailService.getById(id);

        return R.ok().put("orderTaskDetail", orderTaskDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderTaskDetailEntity orderTaskDetail) {
        orderTaskDetailService.save(orderTaskDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderTaskDetailEntity orderTaskDetail) {
        orderTaskDetailService.updateById(orderTaskDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        orderTaskDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

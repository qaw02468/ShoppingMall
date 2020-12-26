package tw.yu.shoppingmall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.order.entity.SettingEntity;
import tw.yu.shoppingmall.order.service.SettingService;

import java.util.Arrays;
import java.util.Map;


/**
 * 訂單配置信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@RestController
@RequestMapping("order/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = settingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SettingEntity setting = settingService.getById(id);

        return R.ok().put("setting", setting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SettingEntity setting) {
        settingService.save(setting);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SettingEntity setting) {
        settingService.updateById(setting);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        settingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

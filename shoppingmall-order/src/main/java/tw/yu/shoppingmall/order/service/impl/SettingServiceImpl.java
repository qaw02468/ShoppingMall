package tw.yu.shoppingmall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.order.dao.SettingDao;
import tw.yu.shoppingmall.order.entity.SettingEntity;
import tw.yu.shoppingmall.order.service.SettingService;

import java.util.Map;


@Service("settingService")
public class SettingServiceImpl extends ServiceImpl<SettingDao, SettingEntity> implements SettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SettingEntity> page = this.page(
                new Query<SettingEntity>().getPage(params),
                new QueryWrapper<SettingEntity>()
        );

        return new PageUtils(page);
    }

}
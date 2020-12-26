package tw.yu.shoppingmall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.order.dao.OperateHistoryDao;
import tw.yu.shoppingmall.order.entity.OperateHistoryEntity;
import tw.yu.shoppingmall.order.service.OperateHistoryService;

import java.util.Map;


@Service("operateHistoryService")
public class OperateHistoryServiceImpl extends ServiceImpl<OperateHistoryDao, OperateHistoryEntity> implements OperateHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperateHistoryEntity> page = this.page(
                new Query<OperateHistoryEntity>().getPage(params),
                new QueryWrapper<OperateHistoryEntity>()
        );

        return new PageUtils(page);
    }

}
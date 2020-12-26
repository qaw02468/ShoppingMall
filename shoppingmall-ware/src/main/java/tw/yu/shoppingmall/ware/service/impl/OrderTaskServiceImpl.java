package tw.yu.shoppingmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.ware.dao.OrderTaskDao;
import tw.yu.shoppingmall.ware.entity.OrderTaskEntity;
import tw.yu.shoppingmall.ware.service.OrderTaskService;

import java.util.Map;


@Service("orderTaskService")
public class OrderTaskServiceImpl extends ServiceImpl<OrderTaskDao, OrderTaskEntity> implements OrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderTaskEntity> page = this.page(
                new Query<OrderTaskEntity>().getPage(params),
                new QueryWrapper<OrderTaskEntity>()
        );

        return new PageUtils(page);
    }

}
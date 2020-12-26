package tw.yu.shoppingmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.ware.dao.OrderTaskDetailDao;
import tw.yu.shoppingmall.ware.entity.OrderTaskDetailEntity;
import tw.yu.shoppingmall.ware.service.OrderTaskDetailService;

import java.util.Map;


@Service("orderTaskDetailService")
public class OrderTaskDetailServiceImpl extends ServiceImpl<OrderTaskDetailDao, OrderTaskDetailEntity> implements OrderTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderTaskDetailEntity> page = this.page(
                new Query<OrderTaskDetailEntity>().getPage(params),
                new QueryWrapper<OrderTaskDetailEntity>()
        );

        return new PageUtils(page);
    }

}
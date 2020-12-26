package tw.yu.shoppingmall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.order.dao.ReturnReasonDao;
import tw.yu.shoppingmall.order.entity.ReturnReasonEntity;
import tw.yu.shoppingmall.order.service.ReturnReasonService;

import java.util.Map;


@Service("returnReasonService")
public class ReturnReasonServiceImpl extends ServiceImpl<ReturnReasonDao, ReturnReasonEntity> implements ReturnReasonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReturnReasonEntity> page = this.page(
                new Query<ReturnReasonEntity>().getPage(params),
                new QueryWrapper<ReturnReasonEntity>()
        );

        return new PageUtils(page);
    }

}
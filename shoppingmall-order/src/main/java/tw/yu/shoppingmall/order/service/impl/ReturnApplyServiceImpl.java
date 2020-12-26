package tw.yu.shoppingmall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.order.dao.ReturnApplyDao;
import tw.yu.shoppingmall.order.entity.ReturnApplyEntity;
import tw.yu.shoppingmall.order.service.ReturnApplyService;

import java.util.Map;


@Service("returnApplyService")
public class ReturnApplyServiceImpl extends ServiceImpl<ReturnApplyDao, ReturnApplyEntity> implements ReturnApplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReturnApplyEntity> page = this.page(
                new Query<ReturnApplyEntity>().getPage(params),
                new QueryWrapper<ReturnApplyEntity>()
        );

        return new PageUtils(page);
    }

}
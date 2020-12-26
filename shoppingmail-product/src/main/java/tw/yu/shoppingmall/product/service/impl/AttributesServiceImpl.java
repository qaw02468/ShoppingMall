package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.AttributesDao;
import tw.yu.shoppingmall.product.entity.AttributesEntity;
import tw.yu.shoppingmall.product.service.AttributesService;

import java.util.Map;


@Service("attributesService")
public class AttributesServiceImpl extends ServiceImpl<AttributesDao, AttributesEntity> implements AttributesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttributesEntity> page = this.page(
                new Query<AttributesEntity>().getPage(params),
                new QueryWrapper<AttributesEntity>()
        );

        return new PageUtils(page);
    }

}
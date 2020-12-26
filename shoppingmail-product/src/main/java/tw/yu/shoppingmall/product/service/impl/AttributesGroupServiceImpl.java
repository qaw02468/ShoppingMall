package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.AttributesGroupDao;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupService;

import java.util.Map;


@Service("attributesGroupService")
public class AttributesGroupServiceImpl extends ServiceImpl<AttributesGroupDao, AttributesGroupEntity> implements AttributesGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttributesGroupEntity> page = this.page(
                new Query<AttributesGroupEntity>().getPage(params),
                new QueryWrapper<AttributesGroupEntity>()
        );

        return new PageUtils(page);
    }

}
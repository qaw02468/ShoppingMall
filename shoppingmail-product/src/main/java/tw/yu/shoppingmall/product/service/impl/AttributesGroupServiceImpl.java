package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long cateLogId) {
        String key = (String) params.get("key");

        QueryWrapper<AttributesGroupEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        if (cateLogId == 0) {
            IPage<AttributesGroupEntity> page = this.page(new Query<AttributesGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", cateLogId);
            IPage<AttributesGroupEntity> page = this.page(new Query<AttributesGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

}

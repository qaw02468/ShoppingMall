package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.AttributesGroupRelationDao;
import tw.yu.shoppingmall.product.entity.AttributesGroupRelationEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupRelationService;
import tw.yu.shoppingmall.product.vo.AttributesGroupRelationVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attributesGroupRelationService")
public class AttributesGroupRelationServiceImpl extends ServiceImpl<AttributesGroupRelationDao, AttributesGroupRelationEntity> implements AttributesGroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttributesGroupRelationEntity> page = this.page(
                new Query<AttributesGroupRelationEntity>().getPage(params),
                new QueryWrapper<AttributesGroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatch(List<AttributesGroupRelationVo> vos) {
        List<AttributesGroupRelationEntity> collect = vos.stream()
                .map(item -> {
                    AttributesGroupRelationEntity attributesGroupRelationEntity = new AttributesGroupRelationEntity();
                    BeanUtils.copyProperties(item, attributesGroupRelationEntity);
                    return attributesGroupRelationEntity;
                }).collect(Collectors.toList());

        this.saveBatch(collect);
    }

}
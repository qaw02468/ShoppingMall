package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.AttributesGroupDao;
import tw.yu.shoppingmall.product.entity.AttributesEntity;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.service.AttributesGroupService;
import tw.yu.shoppingmall.product.service.AttributesService;
import tw.yu.shoppingmall.product.vo.AttrGroupWithAttrVo;
import tw.yu.shoppingmall.product.vo.SkuItemSaleAttrGroupVo;
import tw.yu.shoppingmall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attributesGroupService")
public class AttributesGroupServiceImpl extends ServiceImpl<AttributesGroupDao, AttributesGroupEntity> implements AttributesGroupService {

    @Autowired
    private AttributesService attributesService;

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

    @Override
    public List<AttrGroupWithAttrVo> getAttributeGroupWithAttributeByCateLogId(Long cateLogId) {

        List<AttributesGroupEntity> entities = this.list(new QueryWrapper<AttributesGroupEntity>().eq("catelog_id", cateLogId));

        List<AttrGroupWithAttrVo> collect = entities.stream().map(item -> {
            AttrGroupWithAttrVo attrGroupWithAttrVo = new AttrGroupWithAttrVo();

            BeanUtils.copyProperties(item, attrGroupWithAttrVo);
            List<AttributesEntity> relationAttrs = attributesService.getRelationAttr(attrGroupWithAttrVo.getAttrGroupId());
            attrGroupWithAttrVo.setAttrs(relationAttrs);

            return attrGroupWithAttrVo;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<SkuItemSaleAttrGroupVo> getAttributeGroupWithAttributeBySpuId(Long spuId, Long catalogId) {

        AttributesGroupDao baseMapper = this.baseMapper;
        List<SkuItemSaleAttrGroupVo> vos = baseMapper.getAttributeGroupWithAttributeBySpuId(spuId, catalogId);

        return vos;
    }

}

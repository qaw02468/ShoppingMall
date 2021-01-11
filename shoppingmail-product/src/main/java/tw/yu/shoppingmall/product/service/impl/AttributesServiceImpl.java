package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.yu.common.constant.ProductConstant;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.AttributesDao;
import tw.yu.shoppingmall.product.dao.AttributesGroupDao;
import tw.yu.shoppingmall.product.dao.AttributesGroupRelationDao;
import tw.yu.shoppingmall.product.dao.CategoryDao;
import tw.yu.shoppingmall.product.entity.AttributesEntity;
import tw.yu.shoppingmall.product.entity.AttributesGroupEntity;
import tw.yu.shoppingmall.product.entity.AttributesGroupRelationEntity;
import tw.yu.shoppingmall.product.entity.CategoryEntity;
import tw.yu.shoppingmall.product.service.AttributesService;
import tw.yu.shoppingmall.product.service.CategoryService;
import tw.yu.shoppingmall.product.vo.AttributesGroupRelationVo;
import tw.yu.shoppingmall.product.vo.AttributesResponseVo;
import tw.yu.shoppingmall.product.vo.AttributesVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attributesService")
public class AttributesServiceImpl extends ServiceImpl<AttributesDao, AttributesEntity> implements AttributesService {

    @Autowired
    private AttributesGroupRelationDao relationDao;

    @Autowired
    private AttributesGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttributesEntity> page = this.page(
                new Query<AttributesEntity>().getPage(params),
                new QueryWrapper<AttributesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttributes(AttributesVo attributes) {
        AttributesEntity attributesEntity = new AttributesEntity();
        BeanUtils.copyProperties(attributes, attributesEntity);

        this.save(attributesEntity);

        if (attributes.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attributes.getAttrGroupId() != null) {
            AttributesGroupRelationEntity relationEntity = new AttributesGroupRelationEntity();
            relationEntity.setAttrGroupId(attributes.getAttrGroupId());
            relationEntity.setAttrId(attributesEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttributesPage(Map<String, Object> params, Long cateLogId, String type) {
        QueryWrapper<AttributesEntity> queryWrapper = new QueryWrapper<AttributesEntity>()
                .eq("attr_type", "base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (cateLogId != 0) {
            queryWrapper.eq("catelog_id", cateLogId);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id", key)
                        .or()
                        .like("attr_name", key);
            });
        }

        IPage<AttributesEntity> page = this.page(
                new Query<AttributesEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttributesEntity> records = page.getRecords();

        List<AttributesResponseVo> atrResponse = records.stream()
                .map((attributesEntity) -> {
                    AttributesResponseVo atrResponseVo = new AttributesResponseVo();
                    BeanUtils.copyProperties(attributesEntity, atrResponseVo);

                    if ("base".equalsIgnoreCase(type)) {
                        AttributesGroupRelationEntity attrId = relationDao.selectOne(
                                new QueryWrapper<AttributesGroupRelationEntity>().eq("attr_id", attributesEntity.getAttrId()));
                        if (attrId != null && attrId.getAttrGroupId() != null) {
                            AttributesGroupEntity atrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                            atrResponseVo.setGroupName(atrGroupEntity.getAttrGroupName());
                        }
                    }
                    CategoryEntity categoryEntity = categoryDao.selectById(attributesEntity.getCatelogId());

                    if (categoryEntity != null) {
                        atrResponseVo.setCatelogName(categoryEntity.getName());
                    }

                    return atrResponseVo;
                }).collect(Collectors.toList());

        pageUtils.setList(atrResponse);

        return pageUtils;
    }

    @Override
    public AttributesResponseVo getAttributesInfo(Long attrId) {
        AttributesResponseVo attrResponseVo = new AttributesResponseVo();
        AttributesEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrResponseVo);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttributesGroupRelationEntity attrGroupRelation = relationDao.selectOne(
                    new QueryWrapper<AttributesGroupRelationEntity>().eq("attr_id", attrId));

            if (attrGroupRelation != null) {
                attrResponseVo.setAttrGroupId(attrGroupRelation.getAttrGroupId());
                AttributesGroupEntity attributesGroupEntity = attrGroupDao.selectById(attrGroupRelation.getAttrGroupId());
                if (attributesGroupEntity != null) {
                    attrResponseVo.setGroupName(attributesGroupEntity.getAttrGroupName());
                }
            }
        }

        Long catelogId = attrEntity.getCatelogId();
        Long[] cateLogPath = categoryService.findCateLogPath(catelogId);
        attrResponseVo.setCatelogPath(cateLogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            attrResponseVo.setCatelogName(categoryEntity.getName());
        }

        return attrResponseVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttributesVo attributes) {
        AttributesEntity attrEntity = new AttributesEntity();
        BeanUtils.copyProperties(attributes, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            Integer count = relationDao.selectCount(new QueryWrapper<AttributesGroupRelationEntity>().eq("attr_id", attributes.getAttrId()));
            AttributesGroupRelationEntity attrGroupRelationEntity = new AttributesGroupRelationEntity();
            attrGroupRelationEntity.setAttrGroupId(attributes.getAttrGroupId());
            attrGroupRelationEntity.setAttrId(attributes.getAttrId());
            if (count > 0) {

                relationDao.update(
                        attrGroupRelationEntity, new UpdateWrapper<AttributesGroupRelationEntity>().eq("attr_id", attributes.getAttrId()));
            } else {
                relationDao.insert(attrGroupRelationEntity);
            }
        }
    }

    @Override
    public List<AttributesEntity> getRelationAttr(Long attrGroupId) {

        List<AttributesGroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttributesGroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> attrIdList = entities.stream()
                .map(AttributesGroupRelationEntity::getAttrId)
                .collect(Collectors.toList());

        if (attrIdList.size() == 0) {
            return null;
        }
        return this.listByIds(attrIdList);
    }

    @Override
    public void deleteRelation(AttributesGroupRelationVo[] vos) {

        List<AttributesGroupRelationVo> entities = Arrays.stream(vos)
                .peek((item) -> {
                    AttributesGroupRelationEntity attrGroupRelationEntity = new AttributesGroupRelationEntity();
                    BeanUtils.copyProperties(attrGroupRelationEntity, vos);
                }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);

    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId) {

        AttributesGroupEntity attributesGroupEntity = attrGroupDao.selectById(attrGroupId);
        Long catelogId = attributesGroupEntity.getCatelogId();

        List<AttributesGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttributesGroupEntity>()
                .eq("catelog_id", catelogId));

        List<Long> collect = group.stream()
                .map(AttributesGroupEntity::getAttrGroupId)
                .collect(Collectors.toList());

        List<AttributesGroupRelationEntity> groupIds = relationDao.selectList(new QueryWrapper<AttributesGroupRelationEntity>().in("attr_group_id", collect));

        List<Long> attrIds = groupIds.stream()
                .map(AttributesGroupRelationEntity::getAttrId)
                .collect(Collectors.toList());

        QueryWrapper<AttributesEntity> wrapper = new QueryWrapper<AttributesEntity>()
                .eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());


        if (attrIds.size() > 0) {
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttributesEntity> page = this.page(new Query<AttributesEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIdList) {

        return baseMapper.selectSearchAttrIdList(attrIdList);

    }

}
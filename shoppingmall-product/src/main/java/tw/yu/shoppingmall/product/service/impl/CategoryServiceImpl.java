package tw.yu.shoppingmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.CategoryDao;
import tw.yu.shoppingmall.product.entity.CategoryEntity;
import tw.yu.shoppingmall.product.service.CategoryBrandRelationService;
import tw.yu.shoppingmall.product.service.CategoryService;
import tw.yu.shoppingmall.product.vo.CateLog2Vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);

        List<CategoryEntity> level1 = entities.stream()
                .filter((categoryEntity) -> categoryEntity.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildren(menu, entities));
                    return menu;
                }).sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());

        return level1;
    }

    @Override
    public void removeMenuByIds(List<Long> menuList) {
        baseMapper.deleteBatchIds(menuList);
    }

    @Override
    public Long[] findCateLogPath(Long cateLogId) {
        List<Long> path = new ArrayList<>();
        findParentPath(cateLogId, path);

        Collections.reverse(path);

        return path.toArray(new Long[path.size()]);
    }


    @Caching(evict = {
            @CacheEvict(value = "category", key = "'getLevel1Categories'"),
            @CacheEvict(value = "category", key = "'getCateLogJson'")
    })
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public List<CategoryEntity> getLevel1Categories() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<CateLog2Vo>> getCateLogJson() {
        List<CategoryEntity> selectList = baseMapper.selectList(null);

        List<CategoryEntity> level1Categories = getParent_cid(selectList, 0L);

        return level1Categories.stream()
                .collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    List<CategoryEntity> categoryEntity = getParent_cid(selectList, v.getCatId());

                    List<CateLog2Vo> result = null;
                    if (categoryEntity.size() > 0) {
                        result = categoryEntity.stream()
                                .map(l2 -> {
                                    CateLog2Vo cateLog2Vo = new CateLog2Vo(
                                            v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                                    List<CategoryEntity> cateLogLevel3 = getParent_cid(selectList, l2.getCatId());

                                    if (cateLogLevel3.size() > 0) {
                                        List<CateLog2Vo.CateLog3VO> level3List = cateLogLevel3.stream().map(l3 -> {
                                            return new CateLog2Vo.CateLog3VO(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                        }).collect(Collectors.toList());
                                        cateLog2Vo.setCateLog3List(level3List);
                                    }

                                    return cateLog2Vo;
                                }).collect(Collectors.toList());
                    }
                    return result;
                }));
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        return selectList.stream()
                .filter(o -> o.getParentCid().equals(parentCid))
                .collect(Collectors.toList());
    }

    private void findParentPath(Long cateLogId, List<Long> path) {
        path.add(cateLogId);
        CategoryEntity id = this.getById(cateLogId);

        if (id.getParentCid() != 0) {
            findParentPath(id.getParentCid(), path);
        }
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

}
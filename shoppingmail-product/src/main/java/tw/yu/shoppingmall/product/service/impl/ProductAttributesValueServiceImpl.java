package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.ProductAttributesValueDao;
import tw.yu.shoppingmall.product.entity.ProductAttributesValueEntity;
import tw.yu.shoppingmall.product.service.ProductAttributesValueService;

import java.util.List;
import java.util.Map;


@Service("productAttributesValueService")
public class ProductAttributesValueServiceImpl extends ServiceImpl<ProductAttributesValueDao, ProductAttributesValueEntity> implements ProductAttributesValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttributesValueEntity> page = this.page(
                new Query<ProductAttributesValueEntity>().getPage(params),
                new QueryWrapper<ProductAttributesValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(List<ProductAttributesValueEntity> collect) {
        this.saveBatch(collect);
    }

}
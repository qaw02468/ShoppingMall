package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.product.dao.SkuSaleAttributesValueDao;
import tw.yu.shoppingmall.product.entity.SkuSaleAttributesValueEntity;
import tw.yu.shoppingmall.product.service.SkuSaleAttributesValueService;
import tw.yu.shoppingmall.product.vo.SkuItemSaleAttrVo;

import java.util.List;
import java.util.Map;


@Service("skuSaleAttributesValueService")
public class SkuSaleAttributesValueServiceImpl extends ServiceImpl<SkuSaleAttributesValueDao, SkuSaleAttributesValueEntity> implements SkuSaleAttributesValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttributesValueEntity> page = this.page(
                new Query<SkuSaleAttributesValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttributesValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId) {

        SkuSaleAttributesValueDao baseMapper = this.baseMapper;
        return baseMapper.getSaleAttrsBySpuId(spuId);

    }

    @Override
    public List<String> getSkuSaleAttrValuesAsStringList(Long skuId) {

        SkuSaleAttributesValueDao dao = this.baseMapper;
        return dao.getSkuSaleAttrValuesAsStringList(skuId);
    }

}
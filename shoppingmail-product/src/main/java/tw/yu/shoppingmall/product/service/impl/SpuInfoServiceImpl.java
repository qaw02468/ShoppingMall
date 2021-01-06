package tw.yu.shoppingmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tw.yu.common.to.SkuReductionTo;
import tw.yu.common.to.SpuBoundTo;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.product.dao.SpuInfoDao;
import tw.yu.shoppingmall.product.entity.*;
import tw.yu.shoppingmall.product.feign.CouponFeign;
import tw.yu.shoppingmall.product.service.*;
import tw.yu.shoppingmall.product.vo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttributesService attributesService;

    @Autowired
    private ProductAttributesValueService productAttributesValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttributesValueService skuSaleAttributesValueService;

    @Autowired
    private CouponFeign couponFeign;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuVo) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());

        this.saveBaseSpuInfo(spuInfoEntity);

        List<String> decript = spuVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));

        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        List<String> images = spuVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        List<BaseAttrs> baseAttrs = spuVo.getBaseAttrs();

        List<ProductAttributesValueEntity> collect = baseAttrs.stream()
                .map(attr -> {
                    ProductAttributesValueEntity valueEntity = new ProductAttributesValueEntity();
                    valueEntity.setAttrId(attr.getAttrId());
                    valueEntity.setAttrName(attributesService.getById(attr.getAttrId()).getAttrName());
                    valueEntity.setAttrValue(attr.getAttrValues());
                    valueEntity.setQuickShow(attr.getShowDesc());
                    valueEntity.setSpuId(spuInfoEntity.getId());

                    return valueEntity;
                }).collect(Collectors.toList());
        productAttributesValueService.saveProductAttr(collect);

        Bounds bound = spuVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bound, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R r = couponFeign.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("遠存保存spu積分信息失敗");
        }

        List<Skus> skus = spuVo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                String defaultImg = "";

                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                List<SkuImagesEntity> imagesEntities = item.getImages().stream()
                        .map(img -> {
                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();

                            skuImagesEntity.setSkuId(skuId);
                            skuImagesEntity.setImgUrl(img.getImgUrl());
                            skuImagesEntity.setDefaultImg(img.getDefaultImg());

                            return skuImagesEntity;
                        }).filter(StringUtils::isEmpty)
                        .collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);

                List<Attr> attrList = item.getAttr();

                List<SkuSaleAttributesValueEntity> skuSaleAttributesValueEntities = attrList.stream().map(attr -> {
                    SkuSaleAttributesValueEntity skuSaleAttributesValueEntity = new SkuSaleAttributesValueEntity();
                    BeanUtils.copyProperties(attr, skuSaleAttributesValueEntity);
                    skuSaleAttributesValueEntity.setSkuId(skuId);

                    return skuSaleAttributesValueEntity;
                }).collect(Collectors.toList());

                skuSaleAttributesValueService.saveBatch(skuSaleAttributesValueEntities);

                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);

                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R r1 = couponFeign.saveSkuReduction(skuReductionTo);

                    if (r1.getCode() != 0) {
                        log.error("遠存保存spu積分信息失敗");
                    }
                }

            });

        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }


}
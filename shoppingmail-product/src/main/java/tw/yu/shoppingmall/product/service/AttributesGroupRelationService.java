package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.AttributesGroupRelationEntity;

import java.util.Map;

/**
 * 屬性&屬性分組關聯
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
public interface AttributesGroupRelationService extends IService<AttributesGroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


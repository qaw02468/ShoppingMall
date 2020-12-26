package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.AttributesEntity;

import java.util.Map;

/**
 * 商品屬性
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
public interface AttributesService extends IService<AttributesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

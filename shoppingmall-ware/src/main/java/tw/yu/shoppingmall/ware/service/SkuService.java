package tw.yu.shoppingmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.ware.entity.SkuEntity;

import java.util.Map;

/**
 * 商品庫存
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
public interface SkuService extends IService<SkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品評價
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


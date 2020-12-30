package tw.yu.shoppingmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三級分類
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);
}


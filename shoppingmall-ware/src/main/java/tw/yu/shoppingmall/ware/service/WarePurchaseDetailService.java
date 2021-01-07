package tw.yu.shoppingmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.ware.entity.WarePurchaseDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:01
 */
public interface WarePurchaseDetailService extends IService<WarePurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<WarePurchaseDetailEntity> listDetailByPurchaseId(Long id);
}


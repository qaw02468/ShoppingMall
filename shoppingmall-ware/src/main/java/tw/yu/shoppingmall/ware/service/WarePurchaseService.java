package tw.yu.shoppingmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.ware.entity.WarePurchaseEntity;
import tw.yu.shoppingmall.ware.vo.MergeVo;
import tw.yu.shoppingmall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 採購信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
public interface WarePurchaseService extends IService<WarePurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void done(PurchaseDoneVo doneVo);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> idList);

    PageUtils queryPageUnreceive(Map<String, Object> params);
}


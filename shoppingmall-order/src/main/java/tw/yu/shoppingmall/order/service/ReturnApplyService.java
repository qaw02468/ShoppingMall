package tw.yu.shoppingmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.order.entity.ReturnApplyEntity;

import java.util.Map;

/**
 * 訂單退貨申請
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
public interface ReturnApplyService extends IService<ReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


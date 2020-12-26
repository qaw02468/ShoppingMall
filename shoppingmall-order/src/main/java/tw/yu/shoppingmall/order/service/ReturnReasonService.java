package tw.yu.shoppingmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.order.entity.ReturnReasonEntity;

import java.util.Map;

/**
 * 退貨原因
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
public interface ReturnReasonService extends IService<ReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package tw.yu.shoppingmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.ware.entity.OrderTaskDetailEntity;

import java.util.Map;

/**
 * 庫存工作單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
public interface OrderTaskDetailService extends IService<OrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


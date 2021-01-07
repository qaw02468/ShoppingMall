package tw.yu.shoppingmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 庫存工作單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


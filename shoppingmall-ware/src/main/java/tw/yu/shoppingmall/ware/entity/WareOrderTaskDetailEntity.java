package tw.yu.shoppingmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 庫存工作單
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
@Data
@TableName("shoppingmall_ware_manage.ware_order_task_detail")
public class WareOrderTaskDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 購買個數
     */
    private Integer skuNum;
    /**
     * 工作單id
     */
    private Long taskId;

}

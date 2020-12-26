package tw.yu.shoppingmall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 退貨原因
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:59:28
 */
@Data
@TableName("order_return_reason")
public class ReturnReasonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 退貨原因名
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 啟用狀態
     */
    private Integer status;
    /**
     * create_time
     */
    private Date createTime;

}

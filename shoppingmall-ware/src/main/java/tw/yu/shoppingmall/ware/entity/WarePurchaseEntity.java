package tw.yu.shoppingmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 採購信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2021-01-07 15:22:00
 */
@Data
@TableName("shoppingmall_ware_manage.ware_purchase")
public class WarePurchaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 採購單id
     */
    @TableId
    private Long id;
    /**
     * 採購人id
     */
    private Long assigneeId;
    /**
     * 採購人名
     */
    private String assigneeName;
    /**
     * 聯繫方式
     */
    private String phone;
    /**
     * 優先級
     */
    private Integer priority;
    /**
     * 狀態
     */
    private Integer status;
    /**
     * 倉庫id
     */
    private Long wareId;
    /**
     * 總金額
     */
    private BigDecimal amount;
    /**
     * 創建日期
     */
    private Date createTime;
    /**
     * 更新日期
     */
    private Date updateTime;

}

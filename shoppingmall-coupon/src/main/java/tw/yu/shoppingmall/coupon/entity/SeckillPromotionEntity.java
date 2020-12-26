package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒殺活動
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_seckill_promotion")
public class SeckillPromotionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 活動標題
     */
    private String title;
    /**
     * 開始日期
     */
    private Date startTime;
    /**
     * 結束日期
     */
    private Date endTime;
    /**
     * 上下線狀態
     */
    private Integer status;
    /**
     * 創建時間
     */
    private Date createTime;
    /**
     * 創建人
     */
    private Long userId;

}

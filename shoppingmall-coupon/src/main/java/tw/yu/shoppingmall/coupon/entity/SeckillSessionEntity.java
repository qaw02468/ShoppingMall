package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒殺活動場次
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_seckill_session")
public class SeckillSessionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 場次名稱
     */
    private String name;
    /**
     * 每日開始時間
     */
    private Date startTime;
    /**
     * 每日結束時間
     */
    private Date endTime;
    /**
     * 啟用狀態
     */
    private Integer status;
    /**
     * 創建時間
     */
    private Date createTime;

}

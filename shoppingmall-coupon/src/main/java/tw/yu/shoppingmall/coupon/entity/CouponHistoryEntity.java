package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 優惠券領取歷史記錄
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_coupon_history")
public class CouponHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 優惠券id
     */
    private Long couponId;
    /**
     * 會員id
     */
    private Long memberId;
    /**
     * 會員名字
     */
    private String memberNickName;
    /**
     * 獲取方式[0->後台贈送；1->主動領取]
     */
    private Integer getType;
    /**
     * 創建時間
     */
    private Date createTime;
    /**
     * 使用狀態[0->未使用；1->已使用；2->已過期]
     */
    private Integer useType;
    /**
     * 使用時間
     */
    private Date useTime;
    /**
     * 訂單id
     */
    private Long orderId;
    /**
     * 訂單號
     */
    private Long orderSn;

}

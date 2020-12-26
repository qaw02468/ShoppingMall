package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 優惠券信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_coupon_info")
public class CouponInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 優惠卷類型[0->全場贈券；1->會員贈券；2->購物贈券；3->註冊贈券]
     */
    private Integer couponType;
    /**
     * 優惠券圖片
     */
    private String couponImg;
    /**
     * 優惠卷名字
     */
    private String couponName;
    /**
     * 數量
     */
    private Integer num;
    /**
     * 金額
     */
    private BigDecimal amount;
    /**
     * 每人限領張數
     */
    private Integer perLimit;
    /**
     * 使用門檻
     */
    private BigDecimal minPoint;
    /**
     * 開始時間
     */
    private Date startTime;
    /**
     * 結束時間
     */
    private Date endTime;
    /**
     * 使用類型[0->全場通用；1->指定分類；2->指定商品]
     */
    private Integer useType;
    /**
     * 備註
     */
    private String note;
    /**
     * 發行數量
     */
    private Integer publishCount;
    /**
     * 已使用數量
     */
    private Integer useCount;
    /**
     * 領取數量
     */
    private Integer receiveCount;
    /**
     * 可以領取的開始日期
     */
    private Date enableStartTime;
    /**
     * 可以領取的結束日期
     */
    private Date enableEndTime;
    /**
     * 優惠碼
     */
    private String code;
    /**
     * 可以領取的會員等級[0->不限等級，其他-對應等級]
     */
    private Integer memberLevel;
    /**
     * 發布狀態[0-未發布，1-已發布]
     */
    private Integer publish;

}

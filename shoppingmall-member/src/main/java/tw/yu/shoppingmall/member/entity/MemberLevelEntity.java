package tw.yu.shoppingmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 會員等級
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
@Data
@TableName("user_member_level")
public class MemberLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 等級名稱
     */
    private String name;
    /**
     * 等級需要的成長值
     */
    private Integer growthPoint;
    /**
     * 是否為默認等級[0->不是；1->是]
     */
    private Integer defaultStatus;
    /**
     * 免運費標準
     */
    private BigDecimal freeFreightPoint;
    /**
     * 每次評價獲取的成長值
     */
    private Integer commentGrowthPoint;
    /**
     * 是否有免郵特權
     */
    private Integer priviledgeFreeFreight;
    /**
     * 是否有會員價格特權
     */
    private Integer priviledgeMemberPrice;
    /**
     * 是否有生日特權
     */
    private Integer priviledgeBirthday;
    /**
     * 備註
     */
    private String note;

}

package tw.yu.shoppingmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 會員收貨地址
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
@Data
@TableName("user_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * 收貨人姓名
     */
    private String name;
    /**
     * 電話
     */
    private String phone;
    /**
     * 郵政編碼
     */
    private String postCode;
    /**
     * 省份/直轄市
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 區
     */
    private String region;
    /**
     * 詳細地址(街道)
     */
    private String detailAddress;
    /**
     * 省市區代碼
     */
    private String areacode;
    /**
     * 是否默認
     */
    private Integer defaultStatus;

}

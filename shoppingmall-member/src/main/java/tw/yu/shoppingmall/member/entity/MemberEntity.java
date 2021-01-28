package tw.yu.shoppingmall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 會員
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
@Data
@TableName("user_member")
public class MemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 會員等級id
     */
    private Long levelId;
    /**
     * 用戶名
     */
    private String username;
    /**
     * 密碼
     */
    private String password;
    /**
     * 暱稱
     */
    private String nickname;
    /**
     * 手機號碼
     */
    private String mobile;
    /**
     * 郵箱
     */
    private String email;
    /**
     * 頭像
     */
    private String header;
    /**
     * 性別
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birth;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 職業
     */
    private String job;
    /**
     * 個性簽名
     */
    private String sign;
    /**
     * 用戶來源
     */
    private Integer sourceType;
    /**
     * 積分
     */
    private Integer integration;
    /**
     * 成長值
     */
    private Integer growth;
    /**
     * 啟用狀態
     */
    private Integer status;
    /**
     * 註冊時間
     */
    private Date createTime;

    private String socialId;
}

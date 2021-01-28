package tw.yu.shoppingmall.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class MemberRegisterVo {
    private String userName;
    private String password;
    private String phone;

}

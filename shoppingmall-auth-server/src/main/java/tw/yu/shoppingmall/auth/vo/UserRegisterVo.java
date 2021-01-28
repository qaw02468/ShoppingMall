package tw.yu.shoppingmall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class UserRegisterVo {
    @Length(min = 6,max = 20,message = "用戶名長度必須在6-20之間")
    @NotEmpty(message = "用戶名必須提交")
    private String userName;

    @Length(min = 6,max = 20,message = "用戶名長度必須在6-20之間")
    @NotEmpty(message = "密碼必須提交")
    private String password;

    @NotEmpty(message = "手機號不能為空")
    @Pattern(regexp = "^[0]([9])[0-9]{8}$", message = "手機號格式不正確")
    private String phone;

}

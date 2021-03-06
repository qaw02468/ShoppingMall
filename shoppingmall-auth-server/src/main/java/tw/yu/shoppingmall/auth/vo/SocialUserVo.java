package tw.yu.shoppingmall.auth.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SocialUserVo {
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "email")
    private String email;
    @JSONField(name = "verified_email")
    private Boolean verifiedEmail;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "given_name")
    private String givenName;
    @JSONField(name = "family_name")
    private String familyName;
    @JSONField(name = "picture")
    private String picture;
    @JSONField(name = "locale")
    private String locale;
}

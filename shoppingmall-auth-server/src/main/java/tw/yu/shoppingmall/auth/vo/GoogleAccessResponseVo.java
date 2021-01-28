package tw.yu.shoppingmall.auth.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class GoogleAccessResponseVo {
    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    @JSONField(name = "scope")
    private String scope;
    @JSONField(name = "token_type")
    private String tokenType;
    @JSONField(name = "id_token")
    private String idToken;
}

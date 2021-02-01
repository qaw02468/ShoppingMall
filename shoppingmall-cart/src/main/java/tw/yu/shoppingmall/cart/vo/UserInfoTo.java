package tw.yu.shoppingmall.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author - a89010531111@gmail.com
 */
@ToString
@Data
public class UserInfoTo {
    private Long userId;
    private String username;
    private String userKey;
    private boolean tempUser = false;
}

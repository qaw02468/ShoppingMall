package tw.yu.shoppingmall.member.exception;

/**
 * @author - a89010531111@gmail.com
 */
public class UserNameExistException extends RuntimeException{
    public UserNameExistException() {
        super("用戶存在");
    }
}

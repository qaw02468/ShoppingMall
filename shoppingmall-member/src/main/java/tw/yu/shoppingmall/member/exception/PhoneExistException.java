package tw.yu.shoppingmall.member.exception;

/**
 * @author - a89010531111@gmail.com
 */
public class PhoneExistException extends RuntimeException {
    public PhoneExistException() {
        super("號碼存在");
    }
}

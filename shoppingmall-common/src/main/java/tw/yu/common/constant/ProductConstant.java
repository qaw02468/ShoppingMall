package tw.yu.common.constant;

/**
 * @author - a89010531111@gmail.com
 */
public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "基本屬性"), ATTR_TYPE_SALE(0, "銷售屬性");

        private int code;
        private String message;

        AttrEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

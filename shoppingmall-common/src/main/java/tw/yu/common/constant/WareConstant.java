package tw.yu.common.constant;

/**
 * @author - a89010531111@gmail.com
 */
public class WareConstant {
    public enum PurchaseStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        RECEIVE(2, "已領取"), FINISH(3, "已完成"),
        HASERROR(4, "有異常");
        private final int code;
        private final String message;

        PurchaseStatusEnum(int code, String message) {
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

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        BUYING(2, "正在采購"), FINISH(3, "已完成"),
        HASERROR(4, "采購失敗");
        private int code;
        private String message;

        PurchaseDetailStatusEnum(int code, String message) {
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

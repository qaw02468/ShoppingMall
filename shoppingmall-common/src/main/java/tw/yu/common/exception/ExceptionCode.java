package tw.yu.common.exception;


/**
 * @author - a89010531111@gmail.com
 */

public enum ExceptionCode {
    UNKNOW_EXCEPTION(10000, "系統未知異常"),
    VAILD_EXCEPTION(10001, "參數格式校驗失敗"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架異常"),
    USER_EXIST_EXCEPTION(15001, "用戶已經存在"),
    PHONE_EXIST_EXCEPTION(15002, "手機號已經存在"),
    LOGINACTT_PASSWORD_ERROR(15003, "帳號或密碼錯誤"),
    SOCIALUSER_LOGIN_ERROR(15004, "社交帳號登錄失敗"),
    NOT_STOCK_EXCEPTION(21000, "商品庫存不足");

    private int code;
    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

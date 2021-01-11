package tw.yu.common.exception;


/**
 * @author - a89010531111@gmail.com
 */

public enum ExceptionCode {
    UNKNOW_EXCEPTION(10000, "系統未知異常"),
    VAILD_EXCEPTION(10001, "參數格式校驗失敗"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架異常");


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

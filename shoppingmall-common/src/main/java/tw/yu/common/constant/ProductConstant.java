package tw.yu.common.constant;

import lombok.Getter;

/**
 * @author - a89010531111@gmail.com
 */
public class ProductConstant {

    @Getter
    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "基本屬性"), ATTR_TYPE_SALE(0, "銷售屬性");

        private int code;
        private String message;

        AttrEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum StatusEnum {

        SPU_NEW(0, "新建"),

        SPU_UP(1, "上架"),

        SPU_DOWN(2, "下架");

        private int code;

        private String message;

        StatusEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}

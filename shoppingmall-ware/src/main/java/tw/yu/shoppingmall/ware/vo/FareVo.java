package tw.yu.shoppingmall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class FareVo {
    private MemberAddressVo memberAddressVo;
    private BigDecimal fare;
}
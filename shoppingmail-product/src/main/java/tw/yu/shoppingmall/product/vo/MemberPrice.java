package tw.yu.shoppingmall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author - a89010531111@gmail.com
 */

@Data
public class MemberPrice {

    private Long id;

    private String name;

    private BigDecimal price;

}
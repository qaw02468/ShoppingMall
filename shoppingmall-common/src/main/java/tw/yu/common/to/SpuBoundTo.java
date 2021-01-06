package tw.yu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SpuBoundTo {
    private Long SpuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}

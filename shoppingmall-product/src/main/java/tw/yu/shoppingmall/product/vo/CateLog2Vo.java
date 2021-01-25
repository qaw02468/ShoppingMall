package tw.yu.shoppingmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateLog2Vo {
    private String cateLogId;
    private List<CateLog3VO> cateLog3List;
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CateLog3VO {
        private String cateLog2Id;
        private String id;
        private String name;

    }
}

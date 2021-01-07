package tw.yu.shoppingmall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}

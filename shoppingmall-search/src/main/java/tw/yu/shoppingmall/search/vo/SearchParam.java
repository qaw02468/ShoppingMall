package tw.yu.shoppingmall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SearchParam {

    private String keyword;
    private Long catalogId;
    private String sort;
    private Integer hasStock;
    private String skuPrice;
    private List<Long> brandId;
    private List<String> attrs;
    private Integer pageNum = 1;
    private String _queryString;

}

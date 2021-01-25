package tw.yu.shoppingmall.search.vo;

import lombok.Data;
import tw.yu.common.to.es.SkuEsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SearchResult {
    private List<SkuEsModel> products;
    private Integer pageNum;
    private Long totalRecords;
    private Integer totalPages;
    private List<Integer> pageNavs;
    private List<BrandVo> brands;
    private List<AttrVo> attrs;
    private List<CatalogVo> catalogs;
    private List<NavVo> navs = new ArrayList<>();
    private List<Long> attrIds = new ArrayList<>();

    @Data
    public static class NavVo {
        private String name;
        private String navValue;
        private String link;
    }

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;


    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;

    }

    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;

    }
}

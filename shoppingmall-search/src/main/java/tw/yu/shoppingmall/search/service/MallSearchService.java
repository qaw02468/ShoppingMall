package tw.yu.shoppingmall.search.service;

import tw.yu.shoppingmall.search.vo.SearchParam;
import tw.yu.shoppingmall.search.vo.SearchResult;

/**
 * @author - a89010531111@gmail.com
 */
public interface MallSearchService {
    SearchResult search(SearchParam param);
}

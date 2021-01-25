package tw.yu.shoppingmall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.yu.shoppingmall.search.service.MallSearchService;
import tw.yu.shoppingmall.search.vo.SearchParam;
import tw.yu.shoppingmall.search.vo.SearchResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author - a89010531111@gmail.com
 */
@Controller
public class SearchController {

    @Autowired
    private MallSearchService searchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());

        SearchResult result = searchService.search(param);

        model.addAttribute("result", result);

        return "list";
    }


}

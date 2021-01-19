package tw.yu.shoppingmall.product.web;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.yu.shoppingmall.product.entity.CategoryEntity;
import tw.yu.shoppingmall.product.service.CategoryService;
import tw.yu.shoppingmall.product.vo.CateLog2Vo;

import java.util.List;
import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntityList = categoryService.getLevel1Categories();

        model.addAttribute("categorys", categoryEntityList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catelog.json")
    public Map<String, List<CateLog2Vo>> getCateLogJson() {

        Map<String, List<CateLog2Vo>> map = categoryService.getCateLogJson();
        return map;
    }
}

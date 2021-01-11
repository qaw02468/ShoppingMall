package tw.yu.shoppingmall.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.yu.common.exception.ExceptionCode;
import tw.yu.common.to.es.SkuEsModel;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.search.service.ProductSaveService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {


    @Resource
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModel) {

        boolean result;

        try {
            result = productSaveService.productStatusUp(skuEsModel);
        } catch (IOException e) {
            log.error("ElasticSaveController 商品上架錯誤: {}", e);
            return R.error(ExceptionCode.PRODUCT_UP_EXCEPTION.getCode(), ExceptionCode.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if (!result) {
            return R.ok();
        } else {
            return R.error(ExceptionCode.PRODUCT_UP_EXCEPTION.getCode(), ExceptionCode.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }
}

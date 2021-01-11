package tw.yu.shoppingmall.search.service;

import org.springframework.stereotype.Service;
import tw.yu.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */

public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> esSkuModels) throws IOException;
}

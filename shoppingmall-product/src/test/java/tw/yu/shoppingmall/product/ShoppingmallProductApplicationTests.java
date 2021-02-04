package tw.yu.shoppingmall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.yu.shoppingmall.product.dao.AttributesGroupDao;
import tw.yu.shoppingmall.product.dao.SkuSaleAttributesValueDao;
import tw.yu.shoppingmall.product.entity.BrandEntity;
import tw.yu.shoppingmall.product.service.BrandService;
import tw.yu.shoppingmall.product.service.SkuInfoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ShoppingmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private AttributesGroupDao attributesGroupDao;

    @Autowired
    private SkuSaleAttributesValueDao skuSaleAttributesValueDao;

    @Autowired
    private SkuInfoService skuInfoService;

    @Test
    public void contextLoads() {

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach(System.out::println);
    }

    @Test
    public void test() throws ExecutionException, InterruptedException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
    }
}

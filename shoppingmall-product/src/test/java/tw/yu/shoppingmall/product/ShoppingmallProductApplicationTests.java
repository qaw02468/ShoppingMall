package tw.yu.shoppingmall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.yu.shoppingmall.product.entity.BrandEntity;
import tw.yu.shoppingmall.product.service.BrandService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ShoppingmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach(System.out::println);
    }

}

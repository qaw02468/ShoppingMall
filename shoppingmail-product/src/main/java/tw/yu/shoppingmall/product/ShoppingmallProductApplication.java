package tw.yu.shoppingmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("tw.yu.shoppingmall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class ShoppingmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingmallProductApplication.class, args);
    }

}

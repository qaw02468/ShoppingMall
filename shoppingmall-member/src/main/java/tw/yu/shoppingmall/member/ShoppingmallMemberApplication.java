package tw.yu.shoppingmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShoppingmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingmallMemberApplication.class, args);
    }

}

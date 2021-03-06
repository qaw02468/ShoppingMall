package tw.yu.shoppingmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableFeignClients(basePackages = "tw.yu.shoppingmall.member.feign")
@SpringBootApplication
@EnableDiscoveryClient
public class ShoppingmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingmallMemberApplication.class, args);
    }

}

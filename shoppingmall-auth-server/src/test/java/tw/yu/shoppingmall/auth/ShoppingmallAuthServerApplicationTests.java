package tw.yu.shoppingmall.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.yu.shoppingmall.auth.config.GoogleOath2Config;

@SpringBootTest
class ShoppingmallAuthServerApplicationTests {
    @Autowired
    private GoogleOath2Config googleOath2Config;

    @Test
    void contextLoads() {
        System.out.println(googleOath2Config);
    }

}

package tw.yu.shoppingmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author - a89010531111@gmail.com
 */

@Component
@Data
@ConfigurationProperties(prefix = "shoppingmall.thread")
public class MyThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;

}

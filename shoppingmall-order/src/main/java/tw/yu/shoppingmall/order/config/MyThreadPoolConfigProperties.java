package tw.yu.shoppingmall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author - a89010531111@gmail.com
 */
@ConfigurationProperties(prefix = "shoppingmall.thread")
@Component
@Data
public class MyThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}

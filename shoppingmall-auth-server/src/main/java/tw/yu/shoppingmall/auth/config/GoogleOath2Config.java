package tw.yu.shoppingmall.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author - a89010531111@gmail.com
 */
@PropertySource(value = {"classpath:googleOath2.properties"})
@Component
@Data
public class GoogleOath2Config {

    @Value("${client_id}")
    private String client_id;

    @Value("${auth_uri}")
    private String auth_uri;

    @Value("${token_uri}")
    private String token_uri;

    @Value("${client_secret}")
    private String client_secret;

    @Value("${redirect_uris}")
    private String redirect_uris;

    @Value("${grant_type}")
    private String grant_type;
}

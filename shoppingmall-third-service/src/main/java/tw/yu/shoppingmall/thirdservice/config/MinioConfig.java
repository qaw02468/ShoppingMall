package tw.yu.shoppingmall.thirdservice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - a89010531111@gmail.com
 */

@Configuration
public class MinioConfig {

    @Value(value = "${minio.url}")
    private String url;
    @Value(value = "${minio.access}")
    private String access;
    @Value(value = "${minio.secret}")
    private String secret;
    @Value(value = "${minio.bucket}")
    private String bucket;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(access, secret)
                .build();
    }
}

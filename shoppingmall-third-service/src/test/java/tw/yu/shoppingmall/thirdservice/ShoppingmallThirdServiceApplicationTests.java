package tw.yu.shoppingmall.thirdservice;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
class ShoppingmallThirdServiceApplicationTests {

    @Value(value = "${minio.url}")
    private String url;
    @Value(value = "${minio.access}")
    private String access;
    @Value(value = "${minio.secret}")
    private String secret;
    @Value(value = "${minio.bucket}")
    private String bucket;

    @Test
    public void uploadFileMinio() {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(url)
                            .credentials(access, secret)
                            .build();

            File file = new File("C:\\Users\\user\\Downloads\\a88.png");
            InputStream in = new FileInputStream(file);
            String fileName = file.getName();
            //文件上传到minio上的Name把文件后缀带上，不然下载出现格式问题

            //创建头部信息
            Map<String, String> headers = new HashMap<>(1 << 2);
            //添加自定义内容类型
            headers.put("Content-Type", "application/octet-stream");
            //添加存储类
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
            //添加自定义/用户元数据
            Map<String, String> userMetadata = new HashMap<>(1 << 2);
            userMetadata.put("My-Project", "Project One");
            //上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(fileName).stream(
                            in, in.available(), -1)
                            .headers(headers)
                            .userMetadata(userMetadata)
                            .build());
            in.close();

            log.info("上傳成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package tw.yu.shoppingmall.thirdservice.contoller;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.yu.common.utils.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author - a89010531111@gmail.com
 */

@RestController
public class OssController {

    @Autowired
    private MinioClient minioClient;

    @RequestMapping("/oss/policy")
    public R presignedPutObject(@RequestParam("name") String name, @RequestParam("bucketName") String bucketName) {
        String url = "";

        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(name)
                            .expiry(1, TimeUnit.DAYS)
                            .extraQueryParams(reqParams)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(url);
        return R.ok().put("data", url);
    }
}

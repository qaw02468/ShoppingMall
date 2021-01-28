package tw.yu.shoppingmall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.yu.common.constant.AuthServerConstant;
import tw.yu.common.utils.HttpUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.auth.config.GoogleOath2Config;
import tw.yu.shoppingmall.auth.feign.MemberFeign;
import tw.yu.shoppingmall.auth.vo.GoogleAccessResponseVo;
import tw.yu.common.to.MemberResponseVo;
import tw.yu.shoppingmall.auth.vo.SocialUserVo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */

@Slf4j
@Controller
@RequestMapping("/oauth2.0")
public class Oath2Controller {

    @Autowired
    private GoogleOath2Config googleOath2Config;

    @Autowired
    private MemberFeign memberFeign;

    @GetMapping("/google/success")
    public String google(@RequestParam("code") String code, HttpSession session) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", googleOath2Config.getClient_id());
        map.put("client_secret", googleOath2Config.getClient_secret());
        map.put("grant_type", googleOath2Config.getGrant_type());
        map.put("redirect_uri", googleOath2Config.getRedirect_uris());
        map.put("code", code);

        Map<String, String> headers = new HashMap<>();
        HttpResponse response = HttpUtils.doPost("https://oauth2.googleapis.com",
                "/token", "post", headers, null, map);
        if (response.getStatusLine().getStatusCode() == 200) {
            String json = EntityUtils.toString(response.getEntity());
            GoogleAccessResponseVo accessResponseVo = JSON.parseObject(json, GoogleAccessResponseVo.class);
            SocialUserVo googleUserInfo = getGoogleUserInfo(accessResponseVo);

            R login = memberFeign.login(googleUserInfo);
            if (login.getCode() == 0) {
                MemberResponseVo responseVo = login.getData("data", new TypeReference<MemberResponseVo>() {});

                log.info("\n歡迎 [" + responseVo.getUsername() + "] 使用社交帳號登入");
                session.setAttribute(AuthServerConstant.LOGIN_USER, responseVo);
                return "redirect:http://shoppingmall.com";
            } else {
                return "redirect:http://auth.shoppingmall.com/login.html";
            }
        } else {
            return "redirect:http://auth.shoppingmall.com/login.html";
        }
    }

    private SocialUserVo getGoogleUserInfo(GoogleAccessResponseVo accessResponseVo) throws Exception {
        Map<String, String> map = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        map.put("access_token", accessResponseVo.getAccessToken());

        HttpResponse userInfoResponse = HttpUtils.doGet("https://www.googleapis.com",
                "/oauth2/v2/userinfo", "get", headers, map);

        String json = EntityUtils.toString(userInfoResponse.getEntity());
        SocialUserVo socialUserVo = JSON.parseObject(json, SocialUserVo.class);

        return socialUserVo;
    }
}

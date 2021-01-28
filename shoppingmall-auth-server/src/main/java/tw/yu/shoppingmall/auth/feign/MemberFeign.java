package tw.yu.shoppingmall.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.auth.vo.SocialUserVo;
import tw.yu.shoppingmall.auth.vo.UserLoginVo;
import tw.yu.shoppingmall.auth.vo.UserRegisterVo;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-member")
public interface MemberFeign {

    @PostMapping("/member/member/register")
    R register(@RequestBody UserRegisterVo userRegisterVo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo userLoginVo);

    @PostMapping("/member/member/oauth2/login")
    R login(@RequestBody SocialUserVo socialUser);
}

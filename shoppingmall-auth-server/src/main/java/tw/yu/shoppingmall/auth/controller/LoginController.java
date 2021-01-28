package tw.yu.shoppingmall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tw.yu.common.constant.AuthServerConstant;
import tw.yu.common.to.MemberResponseVo;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.auth.feign.MemberFeign;
import tw.yu.shoppingmall.auth.vo.UserLoginVo;
import tw.yu.shoppingmall.auth.vo.UserRegisterVo;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author - a89010531111@gmail.com
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private MemberFeign memberFeign;

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo registerVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> map = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField,
                            fieldError -> fieldError.getDefaultMessage()));

            redirectAttributes.addFlashAttribute("errors", map);
            return "redirect:http://auth.shoppingmall.com/register.html";
        }

        R response = memberFeign.register(registerVo);

        if (response.getCode() == 0) {

        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", response.getData("msg", new TypeReference<String>() {
            }));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.shoppingmall.com/register.html";
        }

        return "redirect:http://auth.shoppingmall.com/login.html";
    }

    @GetMapping({"/login.html","/","/index","/index.html"})
    public String loginPage(HttpSession session){
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(attribute == null){
            return "login";
        }
        return "redirect:http:/shopppingmall.com";
    }

    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo, RedirectAttributes redirectAttributes, HttpSession session) {
        R response = memberFeign.login(userLoginVo);

        if (response.getCode() == 0) {
            MemberResponseVo memberResponseVo = response.getData("data", new TypeReference<MemberResponseVo>() {
           });
           session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
            log.info("\n歡迎 [" + memberResponseVo.getUsername() + "] 登錄");
        } else {
            HashMap<String, String> error = new HashMap<>();
            error.put("msg", response.getData("msg", new TypeReference<String>() {}));
            redirectAttributes.addFlashAttribute("errors", error);
            return "redirect:http://auth.glmall.com/login.html";
        }
        return "redirect:http://shoppingmall.com";
    }
}

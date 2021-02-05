package tw.yu.shoppingmall.seckill.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import tw.yu.common.constant.AuthServerConstant;
import tw.yu.common.to.MemberResponseVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author - a89010531111@gmail.com
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberResponseVo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        boolean match = new AntPathMatcher().match("/kill", uri);
        if (match) {
            HttpSession session = request.getSession();
            MemberResponseVo memeberResponseVo = (MemberResponseVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
            if (memeberResponseVo != null) {
                threadLocal.set(memeberResponseVo);
                return true;
            } else {
                session.setAttribute("msg", AuthServerConstant.NOT_LOGIN);
                response.sendRedirect("http://auth.shoppingmall.com/login.html");
                return false;
            }
        }
        return true;
    }
}
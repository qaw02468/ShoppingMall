package tw.yu.shoppingmall.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.yu.shoppingmall.order.interceptor.LoginUserInterceptor;
import tw.yu.shoppingmall.order.service.OrderService;

/**
 * @author - a89010531111@gmail.com
 */
@Configuration
public class OrderWebConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginUserInterceptor loginUserInterceptor;

    @Autowired
    private OrderService orderService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor)
                .addPathPatterns("/**");
    }

}

package tw.yu.shoppingmall.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.yu.shoppingmall.cart.interceptor.CartInterceptor;

/**
 * @author - a89010531111@gmail.com
 */
@Configuration
public class ShoppingmallConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
    }
}

package com.xzx.shippingapplication.config;

import com.xzx.shippingapplication.interceptor.CarrierInterceptor;
import com.xzx.shippingapplication.interceptor.OwnerInterceptor;
import com.xzx.shippingapplication.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <Description> MyInterceptorConfig
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.config
 */
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/order/**","/carrier/**")
//                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("test.html")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/city/**")
                .order(0);

        registry.addInterceptor(new OwnerInterceptor())
                .addPathPatterns("/order/**","/carrier/get-all-carrier-name")
                .order(1);

        registry.addInterceptor(new CarrierInterceptor())
                .addPathPatterns("/carrier/**")
                .excludePathPatterns("/carrier/get-all-carrier-name")
                .order(1);

//        super.addInterceptors(registry);
    }

    /**
     * 解决swagger静态资源问题
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决thymleaf无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }


}


package com.qx.demo.config;

import com.qx.demo.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author ZedQ
 * @date 2022年10月03日 21:31 
 * @Description: 注册拦截器
 */


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/user/login", "/user/register", "/**/export", "/**/import","/file/**","/test/**","/kill/**","/role/**","/swagger-resources/**"
                        ,"/webjars/**"
                        ,"/v2/**"
                        ,"/swagger-ui.html/**"
                );
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}

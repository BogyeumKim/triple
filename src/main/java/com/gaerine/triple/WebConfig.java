package com.gaerine.triple;

import com.gaerine.triple.intercepter.TokenIntercepter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig  implements WebMvcConfigurer {

    private final TokenIntercepter intercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(intercepter)
                .addPathPatterns("/**")
                .excludePathPatterns( "/favicon.ico","/login","/**/*.js", "/**/*.css", "/**/*.html","/error","/img/*","/member/newToken","/bootstrap/*");
    }
}

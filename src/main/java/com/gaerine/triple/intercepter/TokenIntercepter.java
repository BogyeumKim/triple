package com.gaerine.triple.intercepter;

import com.gaerine.triple.domain.token.Token;
import com.gaerine.triple.service.token.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenIntercepter implements HandlerInterceptor {

    private final TokenServiceImpl service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> authorizationCookie = Arrays.stream(cookies != null ? cookies : new Cookie[0])
                .filter(cookie -> "Authorization".equals(cookie.getName()))
                .findFirst();

        log.info("authHeader={}",authorizationCookie.get().getValue());
        if (authorizationCookie.isPresent()) {
            String token = authorizationCookie.get().getValue();

            Token validToken = service.validationToken(token);
            if (validToken !=null) {
                log.info("토큰존재함!");
                return true;
            }else{
                log.info("토큰 이상함!");
                response.sendRedirect("/login");
            }
        }else{
            response.sendRedirect("/login");
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

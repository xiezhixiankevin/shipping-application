package com.xzx.shippingapplication.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <Description> TokenInterceptor
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.interceptor
 */
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断用户有没有登陆，登陆之后的用户都有一个对应的 token
        String token = request.getHeader("token");
        if (null == token || "".equals(token)) {
            return false;
        }
        // 返回 true 才会继续执行，返回 false 则取消当前请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}

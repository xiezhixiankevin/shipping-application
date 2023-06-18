package com.xzx.shippingapplication.interceptor;

import cn.itcast.feign.util.UserAccountPackHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.itcast.feign.util.Constant.USER_IDENTITY_OWNER;


@Slf4j
public class OwnerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("在验证货主身份 被拦截了");
        Integer identity = UserAccountPackHolder.getUser().getIdentity();
//        log.debug(String.valueOf(identity));
        if(identity!=USER_IDENTITY_OWNER){
            response.setStatus(401);
            return false;
        }
        System.out.println("鉴权成功！");
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}

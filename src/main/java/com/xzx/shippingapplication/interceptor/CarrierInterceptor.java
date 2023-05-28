package com.xzx.shippingapplication.interceptor;

import com.xzx.shippingapplication.util.UserAccountPackHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.xzx.shippingapplication.util.Constant.USER_IDENTITY_CARRIER;

@Slf4j
public class CarrierInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("被拦截了 在验证承运商的身份 ");

        Integer identity = UserAccountPackHolder.getUser().getIdentity();
        System.out.println(identity);
        if(identity!=USER_IDENTITY_CARRIER){
            response.setStatus(401);
            return false;
        }

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}

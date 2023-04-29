package com.xzx.shippingapplication.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzx.shippingapplication.common.util.JWTUtils;
import com.xzx.shippingapplication.common.util.UserAccountPackHolder;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.xzx.shippingapplication.common.util.Constant.USER_IDENTITY_CARRIER;
import static com.xzx.shippingapplication.common.util.Constant.USER_IDENTITY_OWNER;

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

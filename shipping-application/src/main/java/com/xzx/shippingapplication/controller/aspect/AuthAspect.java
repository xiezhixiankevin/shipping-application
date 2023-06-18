package com.xzx.shippingapplication.controller.aspect;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xzx.shippingapplication.common.util.JWTUtils;
import com.xzx.shippingapplication.common.util.UserAccountPackHolder;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    private HttpSession session;

//    @Before("execution(* com.xzx.shippingapplication.controller.order.ShippingOrderController.*(..))")
    @Before("execution(* com.xzx.shippingapplication.controller.order.ShippingOrderController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        // 从 Session 中获取 token
        String token = (String) session.getAttribute("token");

        // 如果 token 不存在，抛出异常或跳转到错误页面
        if (token == null) {
            throw new RuntimeException("未登录或登录已过期");
            //或者
            //return "error/401";
        }

        // 验证令牌
        DecodedJWT verify = JWTUtils.verify(token);

        // 将用户信息放到 treadLocal 中
        UserAccountPack userAccountPack = new UserAccountPack();
        String idString = verify.getClaim("id").toString();
        String carrierIdString = verify.getClaim("carrierId").toString();

        userAccountPack.setIdentity(verify.getClaim("identity").toString().charAt(1) - '0');
        userAccountPack.setEmail(verify.getClaim("email").toString());
        userAccountPack.setUsername(verify.getClaim("username").toString());
        userAccountPack.setId(Integer.valueOf(idString.substring(1, idString.length() - 1)));
        if (!carrierIdString.equals("Null Claim")) {
            userAccountPack.setCarrierId(Integer.valueOf(carrierIdString.substring(1, carrierIdString.length() - 1)));
        }

        UserAccountPackHolder.saveUser(userAccountPack);
    }
}

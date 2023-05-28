package com.xzx.shippingapplication.interceptor;

import cn.itcast.feign.pojo.pack.UserAccountPack;
import cn.itcast.feign.util.JWTUtils;
import cn.itcast.feign.util.UserAccountPackHolder;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("在token检查与解析 被拦截了");
        Map<String,Object> map = new HashMap<>();
        // 获取请求头中令牌
//        String token = request.getHeader("token");
        String token="" ;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                token=cookie.getValue();
            }
        }

        System.out.println(token);
        try {
            // 验证令牌
            DecodedJWT verify = JWTUtils.verify(token);
//            log.info("用户【"+verify.getClaim("email").asString()+"】正在访问");
            //将用户信息放到treadLocal中

            UserAccountPack userAccountPack = new UserAccountPack();
            String idString = verify.getClaim("id").toString();
            String carrierIdString = verify.getClaim("carrierId").toString();
//            System.out.println(Objects.equals(verify.getClaim("carrierId").toString(), "Null Claim"));

            userAccountPack.setIdentity(verify.getClaim("identity").toString().charAt(1)-'0');
            userAccountPack.setEmail(verify.getClaim("email").toString());
            userAccountPack.setUsername(verify.getClaim("username").toString());
            userAccountPack.setId(Integer.valueOf(idString.substring(1,idString.length()-1)));
            if(!carrierIdString.equals("Null Claim"))userAccountPack.setCarrierId(Integer.valueOf(carrierIdString.substring(1,carrierIdString.length()-1)));
            System.out.println(userAccountPack);
            UserAccountPackHolder.saveUser(userAccountPack);

            return true;  // 放行请求

        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名！");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg","算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","token无效！");
        }
        map.put("state",false);  // 设置状态
        // 将map以json的形式响应到前台  map --> json  (jackson)
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserAccountPackHolder.removeUser();
    }


}

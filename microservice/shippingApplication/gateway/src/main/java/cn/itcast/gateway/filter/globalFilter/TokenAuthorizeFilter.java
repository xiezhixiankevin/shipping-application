package cn.itcast.gateway.filter.globalFilter;


import cn.itcast.feign.pojo.pack.UserAccountPack;
import cn.itcast.feign.util.JWTUtils;
import cn.itcast.feign.util.UserAccountPackHolder;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

//@Order(-1)
@Component
public class TokenAuthorizeFilter implements GlobalFilter, Ordered {

    public final static String ATTRIBUTE_IGNORE_TEST_GLOBAL_FILTER = "@ignoreTestGlobalFilter";
    private static final String AUTHORIZE_TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getAttribute(ATTRIBUTE_IGNORE_TEST_GLOBAL_FILTER) != null) {
            return chain.filter(exchange);
        }
        //1. 获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2. 则获取响应
        ServerHttpResponse response = exchange.getResponse();
//        //3. 如果是user请求则放行
//        if (request.getURI().getPath().contains("/user/login")) {
//            return chain.filter(exchange);
//        }
        //4. 获取请求头
        HttpHeaders headers = request.getHeaders();
        //5. 请求头中获取令牌
        String token = headers.getFirst(AUTHORIZE_TOKEN);

        //6. 判断请求头中是否有令牌
        if (StringUtils.isEmpty(token)) {
            //7. 响应中放入返回的状态吗, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //8. 返回
            return response.setComplete();
        }

        Map<String,Object> map = new HashMap<>();
        //9. 如果请求头中有令牌则解析令牌
        try {
            DecodedJWT verify = JWTUtils.verify(token);

            //封装成UserAccountPackHolder
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


        } catch (Exception e) {
            e.printStackTrace();
            //10. 解析jwt令牌出错, 说明令牌过期或者伪造等不合法情况出现
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //11. 返回
            return response.setComplete();
        }

        //12. 放行
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 1;
    }
}

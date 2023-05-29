package cn.itcast.gateway.filter.globalFilter;

import cn.itcast.feign.util.UserAccountPackHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.itcast.feign.util.Constant.USER_IDENTITY_CARRIER;
import static cn.itcast.gateway.filter.globalFilter.TokenAuthorizeFilter.ATTRIBUTE_IGNORE_TEST_GLOBAL_FILTER;

@Slf4j
public class CarrierAuthorizeFilter implements GlobalFilter, Ordered {

    public final static String ATTRIBUTE_IGNORE_CARRIER_AUTHORIZE_GLOBAL_FILTER = "@IgnoreCarrierAuthorizeGlobalFilter";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getAttribute(ATTRIBUTE_IGNORE_TEST_GLOBAL_FILTER) != null) {
            return chain.filter(exchange);
        }
        if (exchange.getAttribute(ATTRIBUTE_IGNORE_CARRIER_AUTHORIZE_GLOBAL_FILTER) != null) {
            return chain.filter(exchange);
        }
        log.info("在验证货主身份 被拦截了");
        //1. 获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2. 则获取响应
        ServerHttpResponse response = exchange.getResponse();
        //3.鉴权
        Integer identity = UserAccountPackHolder.getUser().getIdentity();
//        log.debug(String.valueOf(identity));
        if(identity!=USER_IDENTITY_CARRIER){
            //7. 响应中放入返回的状态吗, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //8. 返回
            return response.setComplete();
        }
        //12. 放行
        System.out.println("鉴权成功！");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

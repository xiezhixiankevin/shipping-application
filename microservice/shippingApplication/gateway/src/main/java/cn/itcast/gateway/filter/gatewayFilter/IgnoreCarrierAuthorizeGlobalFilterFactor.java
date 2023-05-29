package cn.itcast.gateway.filter.gatewayFilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.itcast.gateway.filter.globalFilter.CarrierAuthorizeFilter.ATTRIBUTE_IGNORE_CARRIER_AUTHORIZE_GLOBAL_FILTER;


@Component
public class IgnoreCarrierAuthorizeGlobalFilterFactor extends AbstractGatewayFilterFactory<IgnoreCarrierAuthorizeGlobalFilterFactor.Config> {

    public IgnoreCarrierAuthorizeGlobalFilterFactor() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this::filter;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ATTRIBUTE_IGNORE_CARRIER_AUTHORIZE_GLOBAL_FILTER, true);
        return chain.filter(exchange);
    }

    public static class Config {

    }

    @Override
    public String name() {
        return "IgnoreCarrierAuthorizeGlobalFilter";
    }
}
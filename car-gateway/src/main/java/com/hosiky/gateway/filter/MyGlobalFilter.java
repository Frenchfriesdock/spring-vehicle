package com.hosiky.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        1 获取请求
        ServerHttpRequest request = exchange.getRequest();
//        2 过滤器处理业务
        HttpHeaders headers = request.getHeaders();
        System.out.println("header = " + headers);
//        3放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
//        过滤器执行顺序，值越小，优先级越高
        return 0;
    }
}

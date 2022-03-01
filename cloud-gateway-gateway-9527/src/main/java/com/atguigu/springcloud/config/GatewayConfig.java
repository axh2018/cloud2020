package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig
{
    //硬编码网关
    @Bean
    public RouteLocator customLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("path_route_atguigu", r -> r.path("/guonei").
                uri("https://news.baidu.com/guonei")).build();
        return routes.build();
    }
}
package com.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-microservice", r -> r.path("/api/users/**", "/api/accounts/**", "/swagger-ui.html")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-MICROSERVICE"))

                .route("scooter-use-microservice", r -> r.path("/api/scooters/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SCOOTER-USE-MICROSERVICE"))

                .route("api/auth", r -> r.path("/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH"))

                .build();
    }

}

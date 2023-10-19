package com.scooter.apiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(HttpMethod.POST, "user/login").permitAll() //le digo q todos los post a login no requieran la authenticion
                            .anyRequest()
                            .authenticated();
                } )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)// descativo el crsf
                .cors(Customizer.withDefaults());

        return http.build();
    }
}

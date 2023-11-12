package com.audition.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()
            .requestMatchers("/actuator/health", "/actuator/info", "/posts**").permitAll()
            .and()
            .authorizeRequests()
            .requestMatchers("/actuator/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
        return http.build();
    }


}

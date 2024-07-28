package com.emeritus.cms.cms_api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /*
     * Lambda DSL
     * In the Lambda DSL there is no need to chain configuration options using the
     * .and() method.
     * The HttpSecurity instance is automatically returned for further configuration
     * after the call to the lambda method.
     * https://spring.io/blog/2019/11/21/spring-security-lambda-dsl
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/register", "/users").permitAll();
                    auth.requestMatchers("/users/**").authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();

        // return http.csrf().disable()
        // .authorizeHttpRequests()
        // .requestMatchers("/users/register","/users").permitAll()
        // .and()
        // .authorizeHttpRequests().requestMatchers("/users/**")
        // .authenticated().and().formLogin().and().build();
    }
}

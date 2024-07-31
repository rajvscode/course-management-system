package com.emeritus.cms.enrollment_service.FeignClients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.emeritus.cms.enrollment_service.security.AuthHeaderFilter;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                // if (authentication != null && authentication.getCredentials() != null) {
                //     String token = (String) authentication.getCredentials();
                //     template.header("Authorization", "Bearer " + token);
                // }

                String authHeaderValue = AuthHeaderFilter.authHeader.get();
                if (authHeaderValue != null) {
                    template.header("Authorization", authHeaderValue);
                }
            }
        };
    }
    
    @Bean
    public FilterRegistrationBean<AuthHeaderFilter> authHeaderFilter() {
        FilterRegistrationBean<AuthHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthHeaderFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
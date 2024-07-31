package com.emeritus.cms.enrollment_service.security;

// import javax.servlet.Filter;
// import javax.servlet.FilterChain;
// import javax.servlet.FilterConfig;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class AuthHeaderFilter implements Filter {

    public static final ThreadLocal<String> authHeader = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authHeaderValue = httpServletRequest.getHeader("Authorization");
        authHeader.set(authHeaderValue);
        try {
            chain.doFilter(request, response);
        } finally {
            authHeader.remove();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
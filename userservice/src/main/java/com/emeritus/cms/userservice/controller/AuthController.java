package com.emeritus.cms.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.userservice.util.JwtUtil;

// @RestController
// @RequestMapping("/auth")
public class AuthController {

    // @Autowired
    // @Lazy
    // private AuthenticationManager authenticationManager;
    
    // @Autowired
    // @Lazy
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    // public AuthController(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) throws Exception {
    //     this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    //     this.jwtUtil = jwtUtil;
    // }

    // @PostMapping("/login")
    public String createToken(Authentication authentication) throws Exception {
        //public String createToken(@RequestBody AuthRequest authRequest) throws Exception {
        // try {
        //     Authentication authentication = authenticationManager.authenticate(
        //             new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        // } catch (AuthenticationException e) {
        //     throw new Exception("Invalid credentials", e);
        // }

        //return jwtUtil.generateToken(authRequest.getUsername());
        return jwtUtil.generateToken(authentication.getName());
    }
}

class AuthRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
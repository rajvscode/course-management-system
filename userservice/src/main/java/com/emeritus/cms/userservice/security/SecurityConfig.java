package com.emeritus.cms.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // @Autowired
    // @Lazy
    // private EmeritusUserDetailsService emeritusUserDetailsService;

    // @Autowired
    // @Lazy
    // private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new EmeritusUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // return http.csrf().disable()
        // .authorizeHttpRequests()
        // .requestMatchers("/users/register","/users").permitAll()
        // .and()
        // .authorizeHttpRequests().requestMatchers("/users/**")
        // .authenticated().and().formLogin().and().build();

        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/register", "/users").permitAll();
                    auth.requestMatchers("/users/**").authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // http
    // .csrf(csrf -> csrf.disable())
    // .authorizeRequests(auth -> auth
    // .requestMatchers("/auth/**").permitAll()
    // .anyRequest().authenticated()
    // )
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // http.addFilterBefore(jwtAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class);

    // return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public AuthenticationManager
    // authenticationManager(AuthenticationConfiguration
    // authenticationConfiguration) throws Exception {
    // return authenticationConfiguration.getAuthenticationManager();
    // }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws
    // Exception {
    // auth.userDetailsService(emeritusUserDetailsService).passwordEncoder(passwordEncoder());
    // }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

package com.snowman.main_auto_service.config;

import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.security.handlers.CarAppAuthenticationEntryPoint;
import com.snowman.main_auto_service.security.jwt.JwtConfigurer;
import com.snowman.main_auto_service.senders.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HOME_ENDPOINT = "/";
    private static final String LOGIN_ENDPOINT = "/log";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String ACCESS_DENIED_ENDPOINT = "/access-denied";

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public SecurityConfig(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() // hack protection, disable for simplicity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // create a session for each user. We do not create. Every request is new
                .and()
                .authorizeRequests() // авторизация запроса
                .antMatchers(HOME_ENDPOINT, LOGIN_ENDPOINT, REGISTRATION_ENDPOINT, ACCESS_DENIED_ENDPOINT).permitAll() // such a pattern - access to all URLs is allowed, without authentication
                .anyRequest().authenticated() // all other requests must be authenticated (either by the admin or by any other role - first you need LogIn, otherwise it will not be allowed)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()) // intercept 403 error
                .and()
                .apply(new JwtConfigurer(userService, tokenService)); // and pass the config in which we check each request for the presence of a token
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CarAppAuthenticationEntryPoint();
    }
}


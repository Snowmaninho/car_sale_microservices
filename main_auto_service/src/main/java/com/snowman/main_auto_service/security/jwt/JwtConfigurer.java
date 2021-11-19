package com.snowman.main_auto_service.security.jwt;

import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.senders.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configurer for Jwt Security
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserService userService;
    private final TokenService tokenService;

    public JwtConfigurer(UserService userService, TokenService tokenService) {

        this.userService = userService;
        this.tokenService = tokenService;
    }

    // each request must pass a filter check before being sent to the server. Our filter checks for a token
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(userService, tokenService);
        // every request that enters our application is first processed by the JwtTokenFilter
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

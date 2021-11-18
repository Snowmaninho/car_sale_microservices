package com.snowman.main_auto_service.security.jwt;

import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.senders.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// конфигуратор для Jwt Securuty
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserService userService;
    private final TokenService tokenService;

    public JwtConfigurer(UserService userService, TokenService tokenService) {

        this.userService = userService;
        this.tokenService = tokenService;
    }

    // каждый запрос перед тем как пройти на сервер должен пройти проверку на фильтр. Наш фильтр проверяет на токен
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(userService, tokenService);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // каждый запрос, который поступает в наше приложение в первую очередь обрабатывает JwtTokenFilter
    }
}

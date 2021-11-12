package com.snowman.main_auto_service.config;

import com.snowman.common_libs.services.UserService;
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
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HOME_ENDPOINT = "/";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";


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
                .csrf().disable() // защита от взлома, отключаем для простоты
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // создавать сессию на каждого юзера. Не создаём. Каждый запрос является новым.
                .and()
                .authorizeRequests() // авторизация запроса
                .antMatchers(HOME_ENDPOINT, LOGIN_ENDPOINT, REGISTRATION_ENDPOINT).permitAll() // такой паттерн - разрешен доступ на все урлы, без аутентификации
                .anyRequest().authenticated() // все остальные запросы должны быть аутентифицированы (либо админом, либо любой другой ролью - сначала нужно LogIn, иначе не пустит)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JwtAuthTokenInCookie")
                .and()
                .apply(new JwtConfigurer(userService, tokenService)); // и передаём конфиг в котором проверяем каждый запрос на наличие токена
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
}


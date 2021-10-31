package com.snowman.main_auto_service.config;

import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.security.jwt.JwtConfigurer;
import com.snowman.main_auto_service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HOME_ENDPOINT = "/";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
                .apply(new JwtConfigurer(jwtTokenProvider, userService)); // и передаём конфиг в котором проверяем каждый запрос на наличие токена
    }

}


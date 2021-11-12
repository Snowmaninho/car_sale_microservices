package com.snowman.auth_service.listeners;

import com.snowman.auth_service.security.jwt.JwtTokenProvider;
import com.snowman.common_libs.configs.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
@EnableRabbit
public class TokenListener {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenListener(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @RabbitListener(queues = RabbitConfig.CREATE_TOKEN_QUEUE)
    public String createToken(String[] usernameAndRole) {
        return jwtTokenProvider.createToken(usernameAndRole[0], usernameAndRole[1]);
    }

    @RabbitListener(queues = RabbitConfig.VALIDATE_TOKEN_QUEUE)
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @RabbitListener(queues = RabbitConfig.RESOLVE_TOKEN_QUEUE)
//    public String resolveToken(Cookie[] cookies) {
    public String resolveToken(Cookie cookie) {
//        return jwtTokenProvider.resolveToken(cookies);
        return jwtTokenProvider.resolveToken(cookie);
    }
}

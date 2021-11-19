package com.snowman.auth_service.listeners;

import com.snowman.auth_service.security.jwt.JwtTokenProvider;
import com.snowman.common_libs.configs.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
@EnableRabbit
@Slf4j
public class TokenListener {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenListener(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @RabbitListener(queues = RabbitConfig.CREATE_TOKEN_QUEUE)
    public String createToken(String[] usernameAndRole) {
        String result = jwtTokenProvider.createToken(usernameAndRole[0], usernameAndRole[1]);
        log.info("IN createToken - created token: " + result);
        return result;
    }

    @RabbitListener(queues = RabbitConfig.VALIDATE_TOKEN_QUEUE)
    public Boolean validateToken(String token) {
        Boolean result = jwtTokenProvider.validateToken(token);
        log.info("IN validateToken - token is valid?: " + result);
        return result;
    }

    @RabbitListener(queues = RabbitConfig.RESOLVE_TOKEN_QUEUE)
    public String resolveToken(Cookie cookie) {
        String result = jwtTokenProvider.resolveToken(cookie);
        log.info("IN resolveToken - resolved token: " + result);
        return result;
    }
}

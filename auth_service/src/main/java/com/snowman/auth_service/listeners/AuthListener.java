package com.snowman.auth_service.listeners;


import com.snowman.auth_service.security.jwt.JwtTokenProvider;
import com.snowman.common_libs.configs.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@EnableRabbit
public class AuthListener {


    private final JwtTokenProvider jwtTokenProvider;

    public AuthListener(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @RabbitListener(queues = RabbitConfig.AUTH_QUEUE)
    public Authentication authenticationListener(String token) {
        return jwtTokenProvider.getAuthentication(token);
    }
}

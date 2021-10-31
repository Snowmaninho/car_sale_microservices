/*
package com.snowman.auth_service.listeners;


import com.snowman.auth_service.security.jwt.JwtProvider;
import com.snowman.common_libs.configs.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TokenListener {

    private final JwtProvider jwtProvider;

    public TokenListener(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @RabbitListener(queues = RabbitConfig.CREATE_TOKEN_QUEUE)
    public String createToken(String username) {
        String token = jwtProvider.createToken(username);
        return token;
    }

    @RabbitListener(queues = RabbitConfig.VALIDATE_TOKEN_QUEUE)
    public boolean validateToken(String token) {
        boolean result = jwtProvider.validateToken(token);
        return result;
    }
}
*/

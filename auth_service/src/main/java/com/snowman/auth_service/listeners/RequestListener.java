/*
package com.snowman.auth_service.listeners;


import com.snowman.auth_service.security.CustomUserDetails;
import com.snowman.auth_service.security.CustomUserDetailsService;
import com.snowman.auth_service.security.jwt.JwtProvider;
import com.snowman.common_libs.configs.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;


@Component
@EnableRabbit
public class RequestListener {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public RequestListener(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }


    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public UsernamePasswordAuthenticationToken requestListener(String token ) {
        String username = jwtProvider.getLoginFromToken(token);
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        return auth;
    }
}
*/

package com.snowman.main_auto_service.senders;

import com.snowman.common_libs.configs.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    private RabbitTemplate template;

    public TokenService(RabbitTemplate template) {
        this.template = template;
    }

    public Authentication getAuthentication(String token) {
        Authentication auth =  (Authentication) template.convertSendAndReceive(RabbitConfig.AUTH_EXCHANGE, RabbitConfig.AUTH_ROUTING_KEY, token);
        return auth;
    }

    public String createToken(String username, String role) {
        String[] usernameAndRole = new String[2];
        usernameAndRole[0] = username;
        usernameAndRole[1] = role;
        String token = (String) template.convertSendAndReceive(RabbitConfig.TOKEN_EXCHANGE, RabbitConfig.CREATE_TOKEN_ROUTING_KEY, usernameAndRole);
        return token;
    }


    public boolean validateToken(String token) {
        boolean result = (boolean) template.convertSendAndReceive(RabbitConfig.TOKEN_EXCHANGE, RabbitConfig.VALIDATE_TOKEN_ROUTING_KEY, token);
        return result;
    }

    public String resolveToken(ServletRequest req) {
        Cookie[] cookies = ((HttpServletRequest) req).getCookies();

        if( cookies == null || cookies.length < 1 ) {
            return null;
        }

        Cookie sessionCookie = null;
        for( Cookie cookie : cookies ) {
            if( ( "JwtAuthTokenInCookie" ).equals( cookie.getName() ) ) {
                sessionCookie = cookie;
                break;
            }
        }

        if (sessionCookie == null) return null;


        String token = (String) template.convertSendAndReceive(RabbitConfig.TOKEN_EXCHANGE, RabbitConfig.RESOLVE_TOKEN_ROUTING_KEY, sessionCookie);
        return token;
    }
}

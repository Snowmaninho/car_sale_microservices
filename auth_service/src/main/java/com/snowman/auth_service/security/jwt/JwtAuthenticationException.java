package com.snowman.auth_service.security.jwt;

import org.springframework.security.core.AuthenticationException;

// создаём Exception для авторизации
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}

package com.snowman.main_auto_service.security.jwt;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.senders.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

// будем фильтровать запросы на наличие токена


@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private final UserService userService;
    private final TokenService tokenService;

    public JwtTokenFilter(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    // каждый запрос который приходит на сервер - валидируется по токену. Нет токена или он не валиден - не будет аутентификации
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = tokenService.resolveToken(req);
        Boolean valid = tokenService.validateToken(token);

        if (token != null && valid) {
            Authentication authentication = tokenService.getAuthentication(token);

            if (authentication != null) {
                String username = authentication.getName();
                AppUser user = userService.findUserByUsername(username);

                Cookie newCookie = new Cookie("JwtAuthTokenInCookie", "Bearer_" + tokenService.createToken(user.getUsername(), user.getRole()));
                newCookie.setMaxAge(600);
                newCookie.setSecure(true);
                newCookie.setHttpOnly(true);
                newCookie.setPath("/");

                ((HttpServletResponse) res).addCookie(newCookie);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(req, res);
    }


}

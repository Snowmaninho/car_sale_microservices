package com.snowman.main_auto_service.security.jwt;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.senders.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// будем фильтровать запросы на наличие токена

public class JwtTokenFilter extends GenericFilterBean {

    private UserService userService;
    private TokenService tokenService;

    public JwtTokenFilter(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    // каждый запрос который приходит на сервер - валидируется по токену. Нет токена или он не валиден - не будет аутентификации
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = tokenService.resolveToken(req);
        if (token != null && tokenService.validateToken(token)) {
            Authentication auth = tokenService.getAuthentication(token);

            if (auth != null) {
                String username = auth.getName();
                AppUser user = userService.findByUsername(username);
                ((HttpServletResponse) res).addCookie(new Cookie("JwtAuthTokenInCookie", "Bearer_" + tokenService.createToken(username, user.getRole())));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }


}

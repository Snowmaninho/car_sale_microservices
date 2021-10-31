package com.snowman.main_auto_service.security.jwt;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.UserService;
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

// будем фильтровать запросы на наличие токена
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    // каждый запрос который приходит на сервер - валидируется по токену. Нет токена или он не валиден - не будет аутентификации
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {

                System.out.println("check: " + auth.getName());
                System.out.println(userService.findByUsername(auth.getName()).getRole());
                String username = auth.getName();
                AppUser user = userService.findByUsername(username);
                ((HttpServletResponse) res).addCookie(new Cookie("JwtAuthTokenInCookie", "Bearer_" + jwtTokenProvider.createToken(username, user.getRole())));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }

}

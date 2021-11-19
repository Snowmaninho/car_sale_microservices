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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//we will filter requests for token availability
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private final UserService userService;
    private final TokenService tokenService;

    public JwtTokenFilter(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    // every request that comes to the server is validated by a token. No token or it is not valid - there will be no authentication
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = tokenService.resolveToken(req);
        Boolean valid = tokenService.validateToken(token);

        // here we update token if it is ok for current user while he is on our site
        if (token != null && valid) {
            Authentication authentication = tokenService.getAuthentication(token);

            if (authentication != null) {
                String username = authentication.getName();
                AppUser user = userService.findUserByUsername(username);

                ((HttpServletResponse) res).addCookie(tokenService.tokenCookie(user));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(req, res);
    }


}

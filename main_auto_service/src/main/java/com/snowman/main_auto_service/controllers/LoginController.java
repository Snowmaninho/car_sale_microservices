package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.configs.RabbitConfig;
import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.security.jwt.JwtTokenProvider;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final RabbitTemplate template;

    public LoginController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider, RabbitTemplate template) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.template = template;
    }

    @GetMapping
    public String login(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Authentication auth = null;
        if (token != null && jwtTokenProvider.validateToken(token)) {
            auth = jwtTokenProvider.getAuthentication(token);
        }
        if (auth != null) return "redirect:/offers";
        return "login";
    }

    @PostMapping
    public String login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            AppUser user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRole());

            Cookie sessionCookie = new Cookie("JwtAuthTokenInCookie", "Bearer_" + token);
            response.addCookie(sessionCookie);

/*            Map<Object, Object> responseEntity = new HashMap<>();
            responseEntity.put("username", username);
            responseEntity.put("token", token);*/

            return "offers";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.UserService;
import com.snowman.main_auto_service.entity.dto.LoginDTO;
import com.snowman.main_auto_service.senders.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/log")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public String login(LoginDTO loginDTO, Model model, Authentication authentication) {

        if (authentication != null) {
            log.info("User already logged: " + authentication.getName());
            return "redirect:/offers";
        }
        return "login";
    }

    @PostMapping
    public String login(@Valid LoginDTO loginDTO, BindingResult bindingResult,
                        Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            AppUser user = userService.findUserByUsername(loginDTO.getUsername());

            if (user == null) {
                log.info("User with username: " + loginDTO.getUsername() + " not found");
                throw new UsernameNotFoundException("User with username: " + loginDTO.getUsername() + " not found");
            }

            String token = tokenService.createToken(user.getUsername(), user.getRole());

            Cookie sessionCookie = new Cookie("JwtAuthTokenInCookie", "Bearer_" + token);
            sessionCookie.setMaxAge(600);
            sessionCookie.setSecure(true);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");

            response.addCookie(sessionCookie);

            log.info("User: " + loginDTO.getUsername() + " successfully login");

            return "redirect:/";

        } catch (AuthenticationException e) {
            log.info("Wrong Username or Password");
            model.addAttribute("loginMessage", "Wrong Username or Password");
            return "login";
        }
    }
}

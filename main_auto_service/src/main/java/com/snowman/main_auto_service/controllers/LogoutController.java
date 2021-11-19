package com.snowman.main_auto_service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

// logout page
@Controller
@Slf4j
@RequestMapping("/out")
public class LogoutController {

    @GetMapping
    public String logoutDo(HttpServletResponse response, Authentication authentication){

        SecurityContextHolder.clearContext();

        // clean up our token cookie
        Cookie responseCookie = new Cookie("JwtAuthTokenInCookie", null);
        responseCookie.setMaxAge(0);
        responseCookie.setSecure(true);
        responseCookie.setHttpOnly(true);
        responseCookie.setPath("/");

        response.addCookie(responseCookie);

        log.info("User: " + authentication.getName() + " successfully logout");
        authentication = null;

        return "redirect:/";
    }
}

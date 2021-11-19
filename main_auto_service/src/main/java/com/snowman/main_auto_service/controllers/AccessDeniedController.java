package com.snowman.main_auto_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// intercept 403 error
@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "accessDenied";
    }
}

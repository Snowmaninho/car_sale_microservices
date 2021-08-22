package com.snowman.main_auto_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingsController {

    @GetMapping
    public String greeting() {
        return "greeting";
    }
}

package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String firstName,
                          @RequestParam String lastName, @RequestParam String email,
                          @RequestParam String password, Model model) {
        AppUser appUserFromDb = userService.findByUsername(username);

        if (appUserFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        AppUser newAppUser = userService.createUser(username, firstName, lastName, email, password);
        userService.register(newAppUser);

        return "login";
    }
}
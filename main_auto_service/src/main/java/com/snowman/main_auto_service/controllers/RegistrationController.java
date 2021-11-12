package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.impl.UserServiceImpl;
import com.snowman.main_auto_service.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {



    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration(RegistrationFormDTO registrationForm, Model model) {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(/*@NonNull @RequestParam String username, @NonNull @RequestParam String firstName,
                          @NonNull @RequestParam String lastName, @NonNull @RequestParam String email,
                          @NonNull @RequestParam String password, @NonNull @RequestParam String confirmPassword*/ @Valid RegistrationFormDTO registrationFormDTO, BindingResult bindingResult, Model model) {

//        AppUser appUserFromDb = userService.findByUsername(username);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        AppUser appUserFromDb = userService.findByUsername(registrationFormDTO.getUsername());

        if (appUserFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        if (!registrationFormDTO.getPassword().equals(registrationFormDTO.getConfirmPassword())) {
            model.addAttribute("message", "Password does not match the confirm password!");
            return "registration";
        }

//        AppUser newAppUser = userService.createUser(username, firstName, lastName, email, password);
        AppUser newAppUser = userService.createUser(registrationFormDTO.getUsername(), registrationFormDTO.getFirstName(), registrationFormDTO.getLastName(), registrationFormDTO.getEmail(), registrationFormDTO.getPassword());
        userService.register(newAppUser);

        return "login";
    }
}
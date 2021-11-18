package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.services.impl.UserServiceImpl;
import com.snowman.main_auto_service.entity.dto.RegistrationFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration(RegistrationFormDTO registrationFormDTO, Model model) {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@Valid RegistrationFormDTO registrationFormDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        AppUser appUserFromDb = userService.findUserByUsername(registrationFormDTO.getUsername());

        if (appUserFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        appUserFromDb = userService.findUserByEmail(registrationFormDTO.getEmail());

        if (appUserFromDb != null) {
            model.addAttribute("message", "This email is already taken!");
            return "registration";
        }

        if (!registrationFormDTO.getPassword().equals(registrationFormDTO.getConfirmPassword())) {
            model.addAttribute("message", "Password does not match the confirm password!");
            return "registration";
        }

        AppUser newAppUser = userService.createUser(registrationFormDTO.getUsername(), registrationFormDTO.getFirstName(), registrationFormDTO.getLastName(), registrationFormDTO.getEmail(), registrationFormDTO.getPassword());
        userService.registerUser(newAppUser);

        return "redirect:/login";
    }
}
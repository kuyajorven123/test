package com.project.test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.project.test.model.UserRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;

    public GlobalControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void addLoggedInUser(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            userRepository.findByEmail(authentication.getName())
                    .ifPresent(user -> model.addAttribute("loggedInUser", user));
        }
    }
}

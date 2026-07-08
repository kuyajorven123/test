package com.project.test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.project.test.model.EmployeeRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final EmployeeRepository employeeRepository;

    public GlobalControllerAdvice(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @ModelAttribute
    public void addLoggedInUser(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            employeeRepository.findByEmail(authentication.getName())
                    .ifPresent(employee -> model.addAttribute("loggedInUser", employee));
        }
    }
}

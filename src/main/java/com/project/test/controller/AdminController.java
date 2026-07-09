package com.project.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


import com.project.test.model.EmployeeRepository;

@Controller
public class AdminController {
    
    @Autowired

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    public AdminController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/admin"})
        public String admin(Model model){
            model.addAttribute("activePage", "admin");
            model.addAttribute("employee", employeeRepository.findByStatusAndRole("Active", "ADMIN"));
            // model.addAttribute("employee", employeeRepository.findByRole("ADMIN"));
            return "pages/admin";
        }
    

}

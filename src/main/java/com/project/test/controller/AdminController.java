package com.project.test.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;



import com.project.test.model.User;
import com.project.test.model.UserRepository;

@Controller
public class AdminController {
    
    @Autowired

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/admin"})
        public String admin(Model model){
            model.addAttribute("activePage", "admin");
            model.addAttribute("user", userRepository.findByStatusAndRole("Active", "ADMIN"));
            // model.addAttribute("employee", userRepository.findByRole("ADMIN"));
            return "pages/admin";
        }
    

    // create
    @PostMapping({"/create_admin"})
    public String create_admin(@ModelAttribute User user){
        Optional<User> existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()){
            return "redirect:/active_employee?error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("Active");
        user.setRole("ADMIN");
        userRepository.save(user);
        return "redirect:/admin?success";
    }
}

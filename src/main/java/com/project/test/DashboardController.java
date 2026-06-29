package com.project.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class DashboardController {

    @GetMapping({"/","/dashboard"})
        public String dashboard(Model model){
            model.addAttribute("activePage", "dashboard");
            return "pages/dashboard";
        }

    @GetMapping({"/settings"})
        public String settings(Model model){
            model.addAttribute("activePage", "settings");
            return "pages/settings";
        }

    @GetMapping({"/admin"})
        public String admin(Model model){
            model.addAttribute("activePage", "admin");
            return "pages/admin";
        }

}

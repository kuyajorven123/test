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

    @GetMapping({"/employee/active_employee"})
        public String active_employee(Model model){
            model.addAttribute("activePage", "active_employee");
            return "pages/active_employee";
        }

    @GetMapping({"/employee/employee_list"})
        public String employee_list(Model model){
            model.addAttribute("activePage", "employee_list");
            return "pages/employee_list";
        }

    @GetMapping({"/employee/inactive_employee"})
        public String inactive_employee(Model model){
            model.addAttribute("activePage", "inactive_employee");
            return "pages/inactive_employee";
        }

    @GetMapping({"/settings"})
        public String settings(Model model){
            model.addAttribute("activePage", "settings");
            return "pages/settings";
        }

}

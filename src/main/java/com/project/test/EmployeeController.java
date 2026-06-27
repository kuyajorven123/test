package com.project.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping({"/employee/active_employee"})
        public String active_employee(Model model){
            model.addAttribute("activePage", "active_employee");
            model.addAttribute("employee", employeeRepository.findAll());
            return "pages/active_employee";
        }
    

    // create
    @PostMapping({"/create"})
    public String create(@ModelAttribute Employee employee){
        Optional<Employee> existingEmployee =
                employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee.isPresent()){
            return "redirect:/employee/active_employee?error";
        }

        employee.setStatus("Active");
        employeeRepository.save(employee);
        return "redirect:/employee/active_employee?success";
    }

}

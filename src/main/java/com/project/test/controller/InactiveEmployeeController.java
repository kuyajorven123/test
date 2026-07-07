package com.project.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.project.test.model.Employee;
import com.project.test.model.EmployeeRepository;


@Controller
public class InactiveEmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    
    @GetMapping({"/inactive_employee/{id}"})
    @ResponseBody
    public Employee getEmployee(@PathVariable Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    

    @GetMapping({"/inactive_employee"})
        public String inactive_employee(Model model){
            model.addAttribute("activePage", "inactive_employee");
             model.addAttribute("employee", employeeRepository.findByStatus("Inactive"));
            return "pages/inactive_employee";
        }
        

    @PostMapping({"/reactivate"})
    public String reactivate(@ModelAttribute Employee employee){
        Employee existing = employeeRepository.findById(employee.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

     existing.setStatus("Active");

     employeeRepository.save(existing);
     return "redirect:/inactive_employee?reactivate_success";
    }


    @PostMapping({"/delete"})
    public String deleted(@RequestParam Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/inactive_employee?delete_success";
    }

}

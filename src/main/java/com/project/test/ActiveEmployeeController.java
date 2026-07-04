package com.project.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.password.PasswordEncoder;



@Controller
public class ActiveEmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    public ActiveEmployeeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/admin/employee/active_employee"})
        public String active_employee(Model model){
            model.addAttribute("activePage", "active_employee");
            model.addAttribute("employee", employeeRepository.findByStatus("Active"));
            return "pages/active_employee";
        }
    

    // create
    @PostMapping({"/admin/create"})
    public String create(@ModelAttribute Employee employee){
        Optional<Employee> existingEmployee =
                employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee.isPresent()){
            return "redirect:/admin/employee/active_employee?error";
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setStatus("Active");
        employee.setRole("EMPLOYEE");
        employeeRepository.save(employee);
        return "redirect:/admin/employee/active_employee?success";
    }


    // id getter
    @GetMapping({"/admin/employee/active_employee/{id}"})
    @ResponseBody
    public Employee getEmployee(@PathVariable Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // edit
    @PostMapping({"/admin/update"})
    public String update(@ModelAttribute Employee employee){

        Employee existing = employeeRepository.findById(employee.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));


            // email checker
        Optional<Employee> existingEmployee =
                employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee.isPresent()
            && !existingEmployee.get().getId().equals(employee.getId())) {

        return "redirect:/admin/employee/active_employee?error";
        }

        existing.setFirstname(employee.getFirstname());
        existing.setMiddlename(employee.getMiddlename());
        existing.setLastname(employee.getLastname());
        existing.setAddress(employee.getAddress());
        existing.setBirthday(employee.getBirthday());
        existing.setPosition(employee.getPosition());
        existing.setEmail(employee.getEmail());

        if (employee.getPassword() != null && !employee.getPassword().isBlank()) {
        existing.setPassword(
                passwordEncoder.encode(employee.getPassword())
        );
    }


        employeeRepository.save(existing);
        return "redirect:/admin/employee/active_employee?update_success";
    }


    // for the deactivate
    @PostMapping({"/admin/deactivate"})
    public String deactivate(@ModelAttribute Employee employee){
        Employee existing = employeeRepository.findById(employee.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

     existing.setStatus("Inactive");

     employeeRepository.save(existing);
     return "redirect:/admin/employee/active_employee?deactivate_success";
    }

    
}

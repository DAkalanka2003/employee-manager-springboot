package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. Read: Display employee list (with optional keyword search) and registration form
    @GetMapping
    public String viewHomePage(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Employee> list = employeeService.searchEmployees(keyword);
        model.addAttribute("listEmployees", list);
        model.addAttribute("employeeCount", list.size());
        model.addAttribute("keyword", keyword);
        if (!model.containsAttribute("employee")) {
            model.addAttribute("employee", new Employee());
        }
        return "employees";
    }

    // 2. Create: Save a new employee with validation checks
    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (employeeService.existsByEmail(employee.getEmail())) {
            result.rejectValue("email", "error.employee", "This email is already registered");
        }
        if (result.hasErrors()) {
            List<Employee> list = employeeService.getAllEmployees();
            model.addAttribute("listEmployees", list);
            model.addAttribute("employeeCount", list.size());
            return "employees";
        }
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
        return "redirect:/employees";
    }

    // 3. Edit: Show the edit form pre-filled with the existing employee details
    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    // 4. Update: Save the updated employee details with validation
    @PostMapping("/{id}/update")
    public String updateEmployee(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (employeeService.existsByEmailAndIdNot(employee.getEmail(), id)) {
            result.rejectValue("email", "error.employee", "This email is already registered");
        }
        if (result.hasErrors()) {
            employee.setId(id);
            return "edit-employee";
        }
        employee.setId(id);
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        return "redirect:/employees";
    }

    // 5. Delete: Remove the employee record by id
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        return "redirect:/employees";
    }
}

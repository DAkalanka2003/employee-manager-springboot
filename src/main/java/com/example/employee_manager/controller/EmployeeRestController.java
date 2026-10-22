package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    // GET /api/employees - Retrieve all employees or filter by keyword
    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(required = false) String keyword) {
        return employeeService.searchEmployees(keyword);
    }

    // GET /api/employees/{id} - Retrieve single employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/employees - Create new employee via JSON body
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
        if (employeeService.existsByEmail(employee.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email is already registered");
        }
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // PUT /api/employees/{id} - Update employee via JSON body
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        Employee existing = employeeService.getEmployeeById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        if (employeeService.existsByEmailAndIdNot(employee.getEmail(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email is already registered");
        }
        employee.setId(id);
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(employee);
    }

    // DELETE /api/employees/{id} - Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        Employee existing = employeeService.getEmployeeById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}

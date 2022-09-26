package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/employees/all")
    public List<Employee> getList() {
        return service.getEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee findOne(@PathVariable String id) {
        Optional<Employee> employee = Optional.empty();
        if (id != null && !id.isEmpty()) {
            employee = service.getEmployee(Long.valueOf(id));
        }
        Employee emp = null;
        if (employee != null && employee.isPresent()) {
            emp = employee.get();
        }
        return emp;
    }

    @PostMapping("/employees/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        Employee emp = service.createEmployee(employee);
        if(emp != null){
            return ResponseEntity.status(HttpStatus.OK).body("success");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
        }
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable String id) {
        if (id != null && !id.isEmpty()) {
            service.deleteEmployee(Long.valueOf(id));
        }

    }
}

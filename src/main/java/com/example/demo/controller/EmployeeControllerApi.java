package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.repository.IOfficeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeControllerApi {
    @Autowired
    private IEmployeeRepository iEmployeeRepository;

    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : fieldErrorList) {
                builder.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
        }
        Employee savedEmployee = iEmployeeRepository.save(employee);
        return new ResponseEntity<>("created succes",HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = iEmployeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int employeeId) {
        Optional<Employee> employee = iEmployeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") int employeeId,
//                                                   @RequestBody Employee employeeDetails) {
//        Optional<Employee> optionalEmployee = iEmployeeRepository.findById(employeeId);
//        if (optionalEmployee.isPresent()) {
//            Employee existingEmployee = optionalEmployee.get();
//            existingEmployee.setName(employeeDetails.getName());
//            existingEmployee.setEmail(employeeDetails.getEmail());
//            existingEmployee.setAge(employeeDetails.getAge());
//            existingEmployee.setOffice(employeeDetails.getOffice());
//            Employee updatedEmployee = iEmployeeRepository.save(existingEmployee);
//            return ResponseEntity.ok(updatedEmployee);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
@PutMapping("/{id}")
public ResponseEntity<Employee> update(@PathVariable("id") int id,@RequestBody Employee employee){
    Optional<Employee> employee1 =iEmployeeRepository.findById(id);
    employee.setId(employee1.get().getId());
    return new ResponseEntity<>(iEmployeeRepository.save(employee),HttpStatus.OK);
}
    // Endpoint to delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") int employeeId) {
        Optional<Employee> optionalEmployee = iEmployeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            iEmployeeRepository.delete(optionalEmployee.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}

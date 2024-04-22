package com.example.demo.service;

import com.example.demo.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(int id);
    void save(Employee employee);
    void delete(int id);
    void edit(Employee employee);
}

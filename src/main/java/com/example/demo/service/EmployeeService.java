package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public List<Employee> findAll() {
        return iEmployeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(int id) {
        return iEmployeeRepository.findById(id);
    }

    @Override
    public void save(Employee employee) {
    iEmployeeRepository.save(employee);
    }

    @Override
    public void delete(int id) {
        iEmployeeRepository.deleteById(id);

    }

    @Override
    public void edit(Employee employee) {
        iEmployeeRepository.save(employee);

    }
}

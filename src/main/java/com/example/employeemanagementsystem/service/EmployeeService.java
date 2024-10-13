package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Map<String, List<Employee>> groupByName() {
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getFirstName));
    }

    public List<Employee> findBetween(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByBirthDateBetween(startDate, endDate);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}

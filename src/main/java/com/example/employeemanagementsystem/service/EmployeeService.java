package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);



    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> findById(Long id) {
        logger.info("Поиск сотрудника по ID: {}", id);
        return employeeRepository.findById(id);
    }

    public Map<String, List<Employee>> groupByName() {
        logger.info("Группировка сотрудников по имени");
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getFirstName));
    }

    public List<Employee> findBetween(LocalDate startDate, LocalDate endDate) {
        logger.info("Поиск сотрудников между датами: {} и {}", startDate, endDate);
        return employeeRepository.findByBirthDateBetween(startDate, endDate);
    }

    public Employee saveEmployee(Employee employee) {
        logger.info("Сохранение сотрудника: {}", employee);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        logger.info("Удаление сотрудника с ID: {}", id);
        employeeRepository.deleteById(id);
    }

    public List<Employee> findAllEmployees() {
        logger.info("Получение всех сотрудников");
        return employeeRepository.findAll();
    }
}
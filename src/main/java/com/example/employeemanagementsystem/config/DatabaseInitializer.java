package com.example.employeemanagementsystem.config;

import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        employeeRepository.save(new Employee("Иван", "Иванов", LocalDate.of(1990, 1, 1), "HR", 50000.0));
        employeeRepository.save(new Employee("Даниил", "Дмитриев", LocalDate.of(2004, 5, 29), "IT", 160000.0));
        employeeRepository.save(new Employee("Сергей", "Сидоров", LocalDate.of(1980, 3, 15), "Финансы", 70000.0));
        employeeRepository.save(new Employee("Анастасия", "Токмакова", LocalDate.of(2003, 10, 28), "Маркетинг", 90000.0));
        employeeRepository.save(new Employee("Дмитрий", "Семенов", LocalDate.of(1988, 11, 5), "Продажи", 72000.0));
        employeeRepository.save(new Employee("Елена", "Григорьева", LocalDate.of(1995, 10, 12), "HR", 48000.0));
    }
}
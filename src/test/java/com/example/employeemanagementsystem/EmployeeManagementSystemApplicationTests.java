package com.example.employeemanagementsystem;


import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeManagementSystemApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	private EmployeeService employeeService;

	@BeforeEach
	void setUp() {
		employeeService = new EmployeeService(employeeRepository);
		employeeRepository.deleteAll();
		System.out.println("Очистка базы данных...");

		employeeRepository.save(new Employee("Иван", "Иванов", LocalDate.of(1990, 1, 1), "HR", 50000.0));
		employeeRepository.save(new Employee("Сергей", "Сидоров", LocalDate.of(1980, 3, 15), "Финансы", 70000.0));
		employeeRepository.save(new Employee("Анастасия", "Токмакова", LocalDate.of(2003, 10, 28), "Маркетинг", 90000.0));

		List<Employee> employees = employeeRepository.findAll();
		System.out.println("Список сотрудников в базе после добавления: " + employees);
	}

	@AfterEach
	void tearDown() {
		employeeRepository.deleteAll();
		System.out.println("База данных очищена после теста.");
	}

	@Test
	void testFindById_ExistingId() {
		List<Employee> employees = employeeRepository.findAll();
		assertFalse(employees.isEmpty(), "Список сотрудников пуст");

		Long employeeId = employees.get(0).getId(); // Получаем ID первого сотрудника
		System.out.println("Ищем сотрудника с ID: " + employeeId);

		Optional<Employee> foundEmployee = employeeService.findById(employeeId);
		assertTrue(foundEmployee.isPresent(), "Сотрудник не найден по ID: " + employeeId);
		System.out.println("Найденный сотрудник: " + foundEmployee.get());
		assertEquals("Иван", foundEmployee.get().getFirstName());
	}

	@Test
	void testFindById_NonExistingId() {
		Optional<Employee> foundEmployee = employeeService.findById(999L);
		assertFalse(foundEmployee.isPresent(), "Найден сотрудник по несуществующему ID.");
		System.out.println("Сотрудник с ID 999 не найден (что ожидается).");
	}

	@Test
	void testGroupByName() {
		Map<String, List<Employee>> groupedEmployees = employeeService.groupByName();
		System.out.println("Группировка сотрудников по именам: " + groupedEmployees);

		assertEquals(1, groupedEmployees.get("Иван").size());
		assertEquals(1, groupedEmployees.get("Сергей").size());
		assertEquals(1, groupedEmployees.get("Анастасия").size());
	}

	@Test
	void testFindBetween_ValidDates() {
		List<Employee> foundEmployees = employeeService.findBetween(LocalDate.of(1980, 1, 1), LocalDate.of(2003, 12, 31));
		System.out.println("Найденные сотрудники между 1980-01-01 и 2003-12-31: " + foundEmployees);
		assertEquals(3, foundEmployees.size());
	}

	@Test
	void testFindBetween_EmptyResult() {
		List<Employee> foundEmployees = employeeService.findBetween(LocalDate.of(1970, 1, 1), LocalDate.of(1975, 12, 31));
		System.out.println("Найденные сотрудники между 1970-01-01 и 1975-12-31: " + foundEmployees);
		assertTrue(foundEmployees.isEmpty(), "Ожидался пустой результат.");
	}

	@Test
	void contextLoads() {
	}

}

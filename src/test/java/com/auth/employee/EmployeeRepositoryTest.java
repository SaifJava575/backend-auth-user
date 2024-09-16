package com.auth.employee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.auth.empl.mockito.Employee;
import com.auth.empl.mockito.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setUp() {
		employee = new Employee("SK ", "SAIFUDDIN", "sksaifuddin575@gmail.com");
	}

	@Test
	@DisplayName("JUnit test for save Employee operation")
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@Test
	@DisplayName("JUnit test for get all Employees operation")
	public void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
		Employee employee1 = new Employee("Aravinth", "Palanisamy", "aravinth.p@dtechideas.com");
		Employee employee2 = new Employee("Balamurugan", "Mani", "balamurugan.m@dtechideas.com");
		Employee employee3 = new Employee("Chandru", "Ravichandran", "chandru.r@dtechideas.com");
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		List<Employee> employees = employeeRepository.findAll();
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(4);
	}

	@Test
	@DisplayName("JUnit test for get Employee By Id operation")
	public void givenEmployee_whenFindById_thenReturnEmployee() {
		employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository.findById(employee.getId()).get();
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getId()).isEqualTo(employee.getId());
	}

	@Test
	@DisplayName("JUnit test for get Employee By Email operation")
	public void givenEmployee_whenFindByEmail_thenReturnEmployee() {
		employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository.findByEmail(employee.getEmail()).get();
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getEmail()).isEqualTo(employee.getEmail());
	}

	@Test
	@DisplayName("JUnit test for update Employee operation")
	public void givenEmployee_whenUpdate_thenReturnEmployee() {
		employeeRepository.save(employee);
		String newEmail = "logicsoft120@gmail.com";
		Employee foundEmployee = employeeRepository.findById(employee.getId()).get();
		foundEmployee.setEmail(newEmail);
		Employee updatedEmployee = employeeRepository.save(foundEmployee);
		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getEmail()).isEqualTo(newEmail);
	}

	@Test
	@DisplayName("JUnit test for delete Employee By Id operation")
	public void givenEmployee_whenDeleteById_thenRemoveEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);
		employeeRepository.deleteById(savedEmployee.getId());
		Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
		assertThat(optionalEmployee).isEmpty();
	}

	@Test
	@DisplayName("JUnit test for custom query using JPQL with Index")
	public void givenFirstNameLastName_whenFindByJPQLFirstNameLastName_thenReturnEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository.findByJPQLFirstNameLastName(savedEmployee.getFirstName(),
				savedEmployee.getLastName());
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
		assertThat(foundEmployee.getLastName()).isEqualTo(savedEmployee.getLastName());
	}

	@Test
	@DisplayName("JUnit test for custom query using JPQL with Named Parameters")
	public void givenFirstNameLastName_whenFindByJPQLNamedParamsFirstNameLastName_thenReturnEmployee() {
		Employee employee = new Employee("Rehena", "Khatun", "rehene@gmail.com");
		Employee savedEmployee = employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository.findByJPQLNamedParamsFirstNameLastName(savedEmployee.getFirstName(),
				savedEmployee.getLastName());
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
		assertThat(foundEmployee.getLastName()).isEqualTo(savedEmployee.getLastName());
	}

	@Test
	@DisplayName("JUnit test for custom query using Native SQL with Index")
	public void givenFirstNameLastName_whenFindByNativeSQLFirstNameLastName_thenReturnEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository.findByNativeSQLFirstNameLastName(savedEmployee.getFirstName(),
				savedEmployee.getLastName());
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
		assertThat(foundEmployee.getLastName()).isEqualTo(savedEmployee.getLastName());
	}

	@Test
	@DisplayName("JUnit test for custom query using Native SQL with Named Parameters")
	public void givenFirstNameLastName_whenFindByNativeSQLNamedParamsFirstNameLastName_thenReturnEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);
		Employee foundEmployee = employeeRepository
				.findByNativeSQLNamedParamsFirstNameLastName(savedEmployee.getFirstName(), savedEmployee.getLastName());
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
		assertThat(foundEmployee.getLastName()).isEqualTo(savedEmployee.getLastName());
	}

	@AfterEach
	public void tearDown() {
		employee = null;
	}

}

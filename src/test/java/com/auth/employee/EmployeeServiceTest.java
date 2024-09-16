package com.auth.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth.empl.mockito.Employee;
import com.auth.empl.mockito.EmployeeRepository;
import com.auth.empl.mockito.EmployeeServiceImpl;
import com.auth.exception.ResourceAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImpl employeeService;

	Employee employee;
	
	@BeforeEach
	public void setUp() {
		employee = new Employee(1L,"Meenakshi", "Ramesh", "sksaifuddin575@gmail.com");
	}

	@AfterEach
	public void tearDown() {
		employee = null;
	}
	
	@Test
	@DisplayName("JUnit test for saveEmployee operation")
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
		given(employeeRepository.save(employee)).willReturn(employee);
		// when - action or the behaviour
		Employee savedEmployee = employeeService.saveEmployee(employee);
		// then - verify the output
		assertThat(savedEmployee).isNotNull();

	}
	
	@Test
	@DisplayName("JUnit test for saveEmployee operation - ResourceAlreadyExists Exception")
	public void givenEmployeeObject_whenSaveEmployee_thenThrowsResourceAlreadyExistsException() {
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
		assertThrows(ResourceAlreadyExistsException.class, () -> {
			employeeService.saveEmployee(employee);
		});
		// then - verify the output
		verify(employeeRepository, never()).save(any(Employee.class));
	}

}

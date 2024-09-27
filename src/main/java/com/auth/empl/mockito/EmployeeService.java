package com.auth.empl.mockito;


import java.util.List;

import com.auth.bean.ApplicatinBean;
import com.auth.bean.SendViaEmailModel;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee);
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(Long id);
	public Employee updateEmployee(Long employeeId, Employee employee);
	public void deleteEmployeeById(Long id);
	public String updateEmployeeInformation(Employee employee);
	public List<?> searchEmployeeData(ApplicatinBean applicationBean);
	public boolean saveEmails(SendViaEmailModel transMailQueueSender);
}

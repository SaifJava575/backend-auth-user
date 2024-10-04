package com.auth.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth.bean.ApplicatinBean;
import com.auth.bean.Response;
import com.auth.bean.SendViaEmailModel;
import com.auth.bean.Status;
import com.auth.empl.mockito.Employee;
import com.auth.empl.mockito.EmployeeService;
import com.auth.utility.UtilProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(value = "http://localhost:4200")
@RestController
//@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UtilProperty utility;
	
   

	@PostMapping(value = "/employee", headers = ("content-type=multipart/*"))
	public ResponseEntity<Employee> createEmployee(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("json") String json) {
		Employee employee = null;
		try {
			employee = new ObjectMapper().readValue(json, Employee.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String filePath = utility.UploadFile(file);
		if (filePath != null && !filePath.equals("")) {
			employee.setFilePath(filePath);
		}
		Employee savedEmployee = this.employeeService.saveEmployee(employee);
		return new ResponseEntity<Employee>(savedEmployee, HttpStatus.CREATED);
	}

	@PostMapping("/searchEmployee")
	public List<?> getAllEmployees(@RequestBody ApplicatinBean applicationBean) {
		List<?> employees =null;
		try {
			employees = employeeService.searchEmployeeData(applicationBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;	
	}

	@GetMapping(path = "employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) {
		Employee foundEmployee = this.employeeService.getEmployeeById(employeeId);
		return new ResponseEntity<Employee>(foundEmployee, HttpStatus.OK);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee) {
		Employee updatedEmployee = this.employeeService.updateEmployee(employeeId, employee);
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	}
	
	@PostMapping("/updateEmployee")
	public @ResponseBody Response<?> updateEmployee(@RequestBody Employee employee) {
		String savedStatus = "";
		try {
			savedStatus = employeeService.updateEmployeeInformation(employee);
			if (savedStatus == "employeeNotExist") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_NOT_FOUND),
						"employee id not exist please enter valid input");
			} else if (savedStatus == "updatedSucessFully") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_OK), savedStatus);
			} else {
				return new Response<>(String.valueOf(HttpServletResponse.SC_BAD_REQUEST),
						"error occured while updating Employee Information");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(String.valueOf(HttpServletResponse.SC_EXPECTATION_FAILED), e.toString());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long employeeId) {
		this.employeeService.deleteEmployeeById(employeeId);
		return new ResponseEntity<String>("Employee with Id : "+employeeId+" deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("/saveemails")
	public Status saveEmails(@RequestBody SendViaEmailModel transMailQueueSender) {
		try {
			if(employeeService.saveEmails(transMailQueueSender))
				return new Status(String.valueOf(HttpServletResponse.SC_OK), "200"); 
			else
				return new Status(String.valueOf(HttpServletResponse.SC_BAD_REQUEST),String.valueOf(HttpServletResponse.SC_OK));
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(String.valueOf(HttpServletResponse.SC_EXPECTATION_FAILED), e.toString());
		}
	}
	
	@GetMapping("viewDocumet/{id}")
	   public @ResponseBody void viewRawPdf(@PathVariable("id") Integer documentId  ,HttpServletResponse response) {		   
		utility.viewRawPdf(documentId, response);
		   
	   }
}

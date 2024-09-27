package com.auth.empl.mockito;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.auth.bean.ApplicatinBean;
import com.auth.bean.JavaConstant;
import com.auth.bean.SendViaEmailModel;
import com.auth.dao.IGenericDao;
import com.auth.exception.ResourceAlreadyExistsException;
import com.auth.exception.ResourceNotFoundException;
import com.auth.utility.UtilProperty;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
 
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private IGenericDao iGenericDao;
	
	@Autowired
	private UtilProperty utilProperty;
	

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	/* When we have a single constructor, we do not have to use @Autowired */
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		Optional<Employee> optionalEmployee = this.employeeRepository.findByEmail(employee.getEmail());
		if (optionalEmployee.isPresent()) {
			throw new ResourceAlreadyExistsException("Employee", "email", employee.getEmail());
		}
		employee.setActiveFlag(true);
		Employee savedEmployee = this.employeeRepository.save(employee);
		Long id=savedEmployee.getId();
		
		Map<String,Object> model=new HashMap<>();
		model.put("empId", id);
		model.put("name", employee.getFirstName());
		model.put("mobileNum", employee.getMobileNum());
		
		try {
			utilProperty.sendEmails("Employee Registration", "templates/employeeRegistration.vm", "",
					model, employee.getEmail(), "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedEmployee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return this.employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		Employee employee = this.employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee","id",id));
		return employee;
	}

	@Override
	public Employee updateEmployee(Long employeeId, Employee employee) {
		Employee foundEmployee = this.employeeRepository.findById(employeeId).orElseThrow( () -> new ResourceNotFoundException("Employee","id",employeeId));
		foundEmployee.setFirstName(employee.getFirstName());
		foundEmployee.setLastName(employee.getLastName());
		foundEmployee.setEmail(employee.getEmail());
		Employee updatedEmployee = employeeRepository.save(foundEmployee);
		return updatedEmployee;
	}

	@Override
	public void deleteEmployeeById(Long id) {
		Employee foundEmployee = this.employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee","id",id));
		this.employeeRepository.deleteById(foundEmployee.getId());
	}
	
	@Override
	@Transactional
	public String updateEmployeeInformation(Employee employee) {
		String updateStatus = "";

		List<?> fetchEmployee = iGenericDao.executeDDLHQL(JavaConstant.UPDATE_EMPLOYEE_INFO,
				new Object[] {employee.getId()});

		if (fetchEmployee != null && fetchEmployee.size() > 0) {
			Employee updateEmployee = (Employee) fetchEmployee.get(0);
			copyNonNullProperties(employee, updateEmployee);
			updateEmployee.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			iGenericDao.update(updateEmployee);
			logger.info("Employee updated: '{}'" + employee.toString());
			updateStatus = "updatedSucessFully";
			logger.info(updateStatus + " " + employee.getId() );
		} else {
			logger.warn(": '{}' " + employee.getFirstName() + " and EmployeeID : "
					+ employee.getId() + " not available!");
			updateStatus = "employeeNotExist";
		}
		return updateStatus;
	}
	
	@Override
	public List<?> searchEmployeeData(ApplicatinBean applicationBean) {
		logger.info("fetching search Employee list: " + new Timestamp(System.currentTimeMillis()));
		List<?> searchEmployee=null;
		try {
			String whereCondition = "where 1=1 ";
			String query=JavaConstant.SEARCH_EMPLOYEE_HISTORY;
			
			if (applicationBean.getId() != null)
				whereCondition += " and emp.id =" + applicationBean.getId();			
			
			if (applicationBean.getActiveFlag() != null)
				whereCondition += " and emp.activeFlag =" + applicationBean.getActiveFlag();
			
			if (applicationBean.getEmail() != null && !applicationBean.getEmail().equals(""))
				whereCondition += " and emp.email like '%" + applicationBean.getEmail() + "%'";
			
			if (applicationBean.getMobileNum() != null && !applicationBean.getMobileNum().equals(""))
				whereCondition += " and emp.mobileNum like '%" + applicationBean.getMobileNum() + "%'";
			
			if (applicationBean.getProfessional() != null && !applicationBean.getProfessional().equals(""))
				whereCondition += " and emp.professional ='" + applicationBean.getProfessional() + "'";

			if (applicationBean.getQualification() != null && !applicationBean.getQualification().equals(""))
				whereCondition += " and emp.qualification ='" + applicationBean.getQualification() + "'";
	
			query+=whereCondition;
			searchEmployee=iGenericDao.executeDDLHQL(query, new Object[] {});

		} catch (Exception e) {
			logger.error("error occcured while fetching search Employee: " + e.getMessage() + e.getCause());
			e.printStackTrace();
		}
		return searchEmployee;
	}
	
	public static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
		
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	
	@Override
	public boolean saveEmails(SendViaEmailModel transMailQueueSender) {
		String resultFlag = utilProperty.sendEmails(transMailQueueSender);
        System.out.println(resultFlag);
		return true;
	}
	
	
	
	

}

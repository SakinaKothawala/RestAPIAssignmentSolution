package com.greatlearning.ems.implementation;

import java.util.List;
import java.util.Optional;

import com.greatlearning.ems.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployee();
	
	Optional<Employee> getAEmployeeById(Integer id);

	List<Employee> getEmployeesCustomSortedByName(String orderDirection);

	List<Employee> searchEmployeeByName(String employeeFirstName);

	String deleteById(Integer id);
	
	Employee addOrUpdateEmployee(Employee employee);
}
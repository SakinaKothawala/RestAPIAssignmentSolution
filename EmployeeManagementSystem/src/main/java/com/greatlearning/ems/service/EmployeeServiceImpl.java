package com.greatlearning.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.greatlearning.ems.entity.Employee;
import com.greatlearning.ems.repository.EmployeeRepository;
import com.greatlearning.ems.implementation.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRespository;
	
	@Override
	public Employee addOrUpdateEmployee(Employee employee) {
		employeeRespository.save(employee);
		employeeRespository.flush();
		return employee;
	}
	

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRespository.findAll();
	}

	@Override
	public List<Employee> getEmployeesCustomSortedByName(String  orderDirection) {
		
		Direction direction =null;
		if("asc".equalsIgnoreCase(orderDirection)) {
			direction = Direction.ASC;
		} else if("desc".equalsIgnoreCase(orderDirection)){
			direction = Direction.DESC;
		}
		else {
			direction = Direction.ASC;
		}
		return employeeRespository.findAll(Sort.by(direction, "firstName"));
	}



	@Override
	public String deleteById(Integer id) {
		employeeRespository.deleteById(id);
		return "Deleted employee id - "+id;
	}


	@Override
	public Optional<Employee> getAEmployeeById(Integer id) {
		return employeeRespository.findById(id);
	}


	@Override
	public List<Employee> searchEmployeeByName(String employeeFirstName) {
		Employee employeeWithFirstName = new Employee();
		employeeWithFirstName.setFirstName(employeeFirstName);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "lastName","email");
		Example<Employee> example = Example.of(employeeWithFirstName, exampleMatcher);
		return employeeRespository.findAll(example, Sort.by("firstName"));
	}

}
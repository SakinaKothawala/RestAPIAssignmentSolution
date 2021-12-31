package com.greatlearning.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.ems.entity.Role;
import com.greatlearning.ems.repository.RoleRepository;
import com.greatlearning.ems.implementation.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role addRole(Role role) {
		roleRepository.save(role);
		roleRepository.flush();
		return role;
	}
	
}


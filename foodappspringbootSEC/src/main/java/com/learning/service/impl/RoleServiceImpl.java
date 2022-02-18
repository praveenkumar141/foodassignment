package com.learning.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Role;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.RoleRepository;
import com.learning.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository Rolerepository;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
	public String addRole(Role role) {
		// TODO Auto-generated method stub
		Role role2 = Rolerepository.save(role);
		if(role2!=null)
			return "roles added";
		return "fail";
	}

	@Override
	public void deleteRole(int roleId) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Role> optional;
		optional = this.getRoleById(roleId);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("record not found");
		}
		else {
			Rolerepository.deleteById(roleId);
			
	}
	}

	@Override
	public Optional<Role> getRoleById(int roleId) {
		// TODO Auto-generated method stub
		return Rolerepository.findById(roleId);
	}
}

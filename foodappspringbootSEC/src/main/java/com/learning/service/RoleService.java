package com.learning.service;

import java.util.Optional;

import com.learning.dto.Role;
import com.learning.exception.IdNotFoundException;


public interface RoleService {

	public String addRole(Role role);
	public void deleteRole(int roleId) throws IdNotFoundException;
	public Optional<Role> getRoleById(int roleId);
}

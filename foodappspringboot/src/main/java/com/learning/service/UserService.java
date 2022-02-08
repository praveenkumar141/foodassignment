package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

public interface UserService {

	public Users addUser(Users users) throws AlreadyExistsException;
	public String updateUser(Integer id,Users users) throws IdNotFoundException;
	public Users getUserById(Integer id) throws IdNotFoundException;
	public Users[] getAllUsers();
	public String deleteUserById(Integer id) throws IdNotFoundException;
	public Optional<List<Users>> getAllUserDetails();
}

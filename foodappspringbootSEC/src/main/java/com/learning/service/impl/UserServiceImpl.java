package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.UserRepository;
import com.learning.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Users addUser(Users users) throws AlreadyExistsException{
		// TODO Auto-generated method stub
		if(userRepository.existsByEmailAndUsername(users.getEmail(), users.getUsername()) == true) {
			throw new AlreadyExistsException("this record already exists");
		}
		Users user = userRepository.save(users);
		
		if(user!=null) {
			return user;
		}
		return null;
	}

	@Override
	public String updateUser(Integer id, Users users) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(!this.userRepository.existsById(id)) {
			throw new IdNotFoundException("Invalid Id");
		}
//		String result= deleteUserById(id);
//		if(result.equals("success")) {
//			users.setId(id);
//			userRepository.save(users);
//			return "success";
//		}
//		return "failure";
		users.setId(id);
		return (this.userRepository.save(users)!=null) ? "success" : "failure";
	}

	@Override
	public Users getUserById(Integer id) throws IdNotFoundException{
		// TODO Auto-generated method stub
		Optional<Users>optional = userRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id doesnt exists");
		}
		else {
			return optional.get();
		}
	}

	@Override
	public Users[] getAllUsers() {
		// TODO Auto-generated method stub
		List<Users> list = userRepository.findAll();
		Users[] array = new Users[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteUserById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Users optional = this.getUserById(id);
		if(optional==null) {
			throw new IdNotFoundException("user record not found");
		}
		else {
			userRepository.deleteById(id);
			return "success";
		}
	}

	@Override
	public Optional<List<Users>> getAllUserDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findAll());
	}

}

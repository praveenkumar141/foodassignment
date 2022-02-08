package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.dto.TYPE;
import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.repository.UserRepository;
import com.learning.service.FoodService;
import com.learning.service.UserService;


@RestController
//@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody Users users) throws AlreadyExistsException {   // ResponseEntity<?> ,,? :- anytype
		
			Users result = userService.addUser(users);
			return ResponseEntity.status(201).body(result); //201:new record created
			
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody Users users){
		
		if(userRepository.existsByEmailAndPassword(users.getEmail(), users.getPassword()) == true) {
			return ResponseEntity.status(200).body("success");
		}
		else {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "user record not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getByid(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		Users users = userService.getUserById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message : ", "user record not found");
		if(users.equals(null))
			return ResponseEntity.status(403).body(map);
		return ResponseEntity.ok(users);
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUserDetails(){
		
		Optional<List<Users>> optional = userService.getAllUserDetails();
		
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "user record not found");
			return ResponseEntity.status(200).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Integer id,@RequestBody Users users) throws IdNotFoundException{
			
		String result = userService.updateUser(id,users);
		if(result.equals("success"))
			return ResponseEntity.status(200).body(result);
		else {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "Sorry,user with "+ id+" not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUserByID(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		if(result.equals("success")) {
			map.put("message : ", "User deleted successfully");
			return ResponseEntity.status(200).body(map);
		}else {
			
			map.put("message : ", "Sorry,user with "+ id+" not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
	
	//FOOD//////FOOD//////FOOD/////
	
	@Autowired
	FoodService foodService;
	
	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping("/food")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {   // ResponseEntity<?> ,,? :- anytype
		
			Food result = foodService.addFood(food);
			return ResponseEntity.status(201).body(result); //201:new record created 
	}
	
	
	@GetMapping("/food/{id}")
	public ResponseEntity<?> getfoodByid(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		Food food = foodService.getFoodById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message : ", "food record not found");
		if(food.equals(null))
			return ResponseEntity.status(403).body(map);
		return ResponseEntity.ok(food);
	}
	
	
	@PutMapping("/food/{id}")
	public ResponseEntity<?> updateFood(@PathVariable("id") Integer id,@RequestBody Food food) throws IdNotFoundException{
			
		String result = foodService.updateFood(id,food);
		if(result.equals("success"))
			return ResponseEntity.status(200).body(result);
		else {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "Sorry,food with "+ id+" not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
	
	@GetMapping("/food")
	public ResponseEntity<?> getAllFoodDetails(){
		
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "food record not found");
			return ResponseEntity.status(200).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
	
	@GetMapping("/food/{foodType}")
	public ResponseEntity<?> getfoodBytype(@PathVariable("foodType")TYPE foodType ) throws IdNotFoundException{
			
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "food record not found");
			return ResponseEntity.status(200).body(map);
		}
		return null;
		
	}
	
	
	@DeleteMapping("/food/{id}")
	public ResponseEntity<?> deletefoodByID(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		String result = foodService.deleteFoodById(id);
		Map<String, String> map = new HashMap<>();
		if(result.equals("success")) {
			map.put("message : ", "food deleted successfully");
			return ResponseEntity.status(200).body(map);
		}else {
			
			map.put("message : ", "Sorry,food with "+ id+" not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
}

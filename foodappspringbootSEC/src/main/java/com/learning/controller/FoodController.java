package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.dto.TYPE;
import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/api")
public class FoodController {  // done everything in usercontroller only

	
	@Autowired
	FoodService foodService;
	
	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping("/food")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {   // ResponseEntity<?> ,,? :- anytype
		
			Food result = foodService.addFood(food);
			return ResponseEntity.status(201).body(result); //201:new record created 
	}
	
	
	@GetMapping("/food/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getfoodByid(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		Food food = foodService.getFoodById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message : ", "food record not found");
		if(food.equals(null))
			return ResponseEntity.status(403).body(map);
		return ResponseEntity.ok(food);
	}
	
	
	@PutMapping("/food/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllFoodDetails(){
		
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "food record not found");
			return ResponseEntity.status(200).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
	
	@GetMapping("/food/dummy/{foodType}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getfoodBytype(@PathVariable("foodType")String foodType ) throws FoodTypeNotFoundException{
			
		Optional<List<Food>> optional = foodService.getFoodByType(foodType);
//		if(optional.isEmpty()) {
//			Map<String, String> map = new HashMap<>();
//			map.put("message : ", "food record not found");
//			return ResponseEntity.status(200).body(map);
//		}
//		
		
		return ResponseEntity.ok(optional.get());
		
	}
	
	
	@DeleteMapping("/food/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
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

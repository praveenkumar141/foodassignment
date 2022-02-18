package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Food;
import com.learning.dto.TYPE;
import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.repository.UserRepository;
import com.learning.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService{

	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public Food addFood(Food food) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		if(foodRepository.existsByFoodNameAndFoodCost(food.getFoodName(), food.getFoodCost()) == true) {
			throw new AlreadyExistsException("this record already exists");
		}
		Food foods = foodRepository.save(food);
		
		if(foods!=null) {
			return foods;
		}
		return null;
	}

	@Override
	public String updateFood(Integer id, Food food) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(!this.foodRepository.existsById(id)) {
			throw new IdNotFoundException("Invalid Id");
		}
		food.setId(id);
		return (this.foodRepository.save(food)!=null) ? "success" : "failure";
	}

	@Override
	public Food getFoodById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Food>optional = foodRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id doesnt exists");
		}
		else {
			return optional.get();
		}
	}

	@Override
	public Food[] getAllFood() {
		// TODO Auto-generated method stub
		List<Food> list = foodRepository.findAll();
		Food[] array = new Food[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteFoodById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Food optional = this.getFoodById(id);
		if(optional==null) {
			throw new IdNotFoundException("food record not found");
		}
		else {
			foodRepository.deleteById(id);
			return "success";
		}
	}

	@Override
	public Optional<List<Food>> getAllFoodDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

	@Override
	public Optional<List<Food>> getFoodByType(String foodType) throws FoodTypeNotFoundException {
		// TODO Auto-generated method stub
		Optional<List<Food>> foodDetailsByType = foodRepository.findByFoodType(TYPE.valueOf(foodType));
		
		if(foodDetailsByType.get().isEmpty()) {
			throw new FoodTypeNotFoundException("Sorry, Food Type Not Found");
		}
		return foodDetailsByType;
	}


}

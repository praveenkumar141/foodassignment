package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.Food;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.FoodTypeNotFoundException;
import com.learning.exception.IdNotFoundException;

public interface FoodService {

	public Food addFood(Food food) throws AlreadyExistsException;
	public String updateFood(Integer id,Food food) throws IdNotFoundException;
	public Food getFoodById(Integer id) throws IdNotFoundException;
	public Food[] getAllFood();
	public String deleteFoodById(Integer id) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails();
	public Optional<List<Food>>getFoodByType(String foodType) throws FoodTypeNotFoundException;
}

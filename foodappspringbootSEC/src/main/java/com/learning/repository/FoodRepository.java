package com.learning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Food;
import com.learning.dto.TYPE;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

	Boolean existsByFoodNameAndFoodCost(String foodName,float foodCost);
	
	Optional<List<Food>> findByFoodType(TYPE foodType);
}

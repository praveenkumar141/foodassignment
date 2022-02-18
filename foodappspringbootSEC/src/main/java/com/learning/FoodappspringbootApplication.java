package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FoodappspringbootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext= SpringApplication.run(FoodappspringbootApplication.class, args);
		
		
		//applicationContext.close();
	}

}

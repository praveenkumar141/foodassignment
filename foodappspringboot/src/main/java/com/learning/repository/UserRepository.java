package com.learning.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Boolean existsByEmailAndName(String email,String name);
	
	Boolean existsByEmailAndPassword(String email,String password);
}

package com.learning.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Boolean existsByEmailAndUsername(String email,String username);
	
	Boolean existsByEmailAndPassword(String email,String password);
	
	Optional<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}

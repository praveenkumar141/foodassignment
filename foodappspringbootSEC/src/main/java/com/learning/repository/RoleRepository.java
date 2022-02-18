package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.EROLE;
import com.learning.dto.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleName(EROLE roleName);
}

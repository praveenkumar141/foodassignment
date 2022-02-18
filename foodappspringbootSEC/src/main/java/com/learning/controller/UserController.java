package com.learning.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.EROLE;
import com.learning.dto.Food;
import com.learning.dto.Role;
import com.learning.dto.TYPE;
import com.learning.dto.Users;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.payload.request.LoginRequest;
import com.learning.payload.request.SignupRequest;
import com.learning.payload.response.JwtResponse;
import com.learning.payload.response.MessageResponse;
import com.learning.repository.FoodRepository;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.security.services.UserDetailsImpl;
import com.learning.service.FoodService;
import com.learning.service.UserService;




@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signupRequest) throws AlreadyExistsException {   // ResponseEntity<?> ,,? :- anytype
		
//		if(userRepository.existsByUsername(signupRequest.getUsername())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//		}
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		Users user = new Users(signupRequest.getUsername(),signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getAddress());
		Set<String> strRoles = signupRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER);//.orElseThrow(()->new RuntimeException("Error: role not found"));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(e->{
				switch(e) {
				
				case "admin":
					Role roleAdmin = roleRepository.findByRoleName(EROLE.ROLE_ADMIN);//.orElseThrow(()->new RuntimeException("Error: role not found"));
					roles.add(roleAdmin);
					System.out.println(roles);
					break;
					
				case "mod":
					Role roleMod = roleRepository.findByRoleName(EROLE.ROLE_MODERATOR);//.orElseThrow(()->new RuntimeException("Error: role not found"));
					roles.add(roleMod);
					break;

				default:
					Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER);//.orElseThrow(()->new RuntimeException("Error: role not found"));
					roles.add(userRole);
					break;
				}
			});
			
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.status(201).body(new MessageResponse("user created successfully"));
			
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl =(UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities().stream().map(i->i.getAuthority()).collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), userDetailsImpl.getUsername(), userDetailsImpl.getEmail(), roles));
	}
	
	
	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getByid(@PathVariable("id") Integer id) throws IdNotFoundException{
		
		Users users = userService.getUserById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message : ", "user record not found");
		if(users.equals(null))
			return ResponseEntity.status(403).body(map);
		return ResponseEntity.ok(users);
	}
	
	
	@GetMapping("/users")
//	@PreAuthorize("hasRole('USER')")
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
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
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
	

	
}

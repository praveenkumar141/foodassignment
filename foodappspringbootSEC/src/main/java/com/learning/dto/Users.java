package com.learning.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="users",uniqueConstraints = {@UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Comparable<Users>{
	
	@Id
	@Column(name="userId")
	@GeneratedValue(strategy = GenerationType.AUTO)     		//Generating ids
	private Integer id;
	
	private String username;
	
	@NotBlank
	@Size(max=60)
	@Email														//email constraint checked
	private String email;
	
	@NotBlank
	@Size(max=100)
	private String password;
	
	//@NotBlank
	@Size(max=100)
	private String address;

	
	@Override
	public int compareTo(Users o) {
		// TODO Auto-generated method stub
		return this.id.compareTo(o.getId());   				//Returning in ascending order
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name= "user_roles",joinColumns = @JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles = new HashSet<>();
	
	
	public Users(String username, String email, String password, String address) {
		this.username=username;
		this.email=email;
		this.password=password;
		this.address=address;
	}
	
}

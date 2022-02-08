package com.learning.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class Users{
	
	@Id
	@Column(name="userId")
	@GeneratedValue(strategy = GenerationType.AUTO)     		//Generating ids
	private Integer id;
	
	private String name;
	
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
	
	
//	@Override
//	public int compareTo(Users o) {
//		// TODO Auto-generated method stub
//		return this.id.compareTo(o.getId());   				//Returning in ascending order
//	}

	
}

package com.learning.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="login")
@AllArgsConstructor
@NoArgsConstructor
public class Login {

	@Id
	private String userName;
	
	@NotBlank
	private String password;
	
	
}

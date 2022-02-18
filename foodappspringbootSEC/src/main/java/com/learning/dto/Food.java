package com.learning.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="food")
@AllArgsConstructor
@NoArgsConstructor
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank
	@Size(max=50)
	private String foodName;
	
	@NotNull
	private float foodCost;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private TYPE foodType;
	
	private String description;
	
	private String foodPic;
}

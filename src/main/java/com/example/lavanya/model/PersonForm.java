package com.example.lavanya.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;





@Entity
@Table (name = "person")
public class PersonForm {

	@Column (name="first_name")
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	
	@Column (name="last_name")
	@NotBlank(message = "lastName is mandatory")
	private String lastName;
	
	@Id
	private int id;
	
	@Column (name="birth_date")
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	
}
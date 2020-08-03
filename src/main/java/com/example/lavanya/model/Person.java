package com.example.lavanya.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;




@Entity // This tells Hibernate to make a table out of this class, allows use of CRUD
@Table (name="person")
public class Person {
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column (name="first_name")
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	
	@Column (name="last_name")
	@NotBlank(message = "lastName is mandatory")
	private String lastName;
	
	@Column (name="birth_date")
	@NotNull
	private LocalDate birthDate;
	
	

	public Person() {

	}

	public Person(String firstName, String lastName, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

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

	
	@Override
	public boolean equals(Object obj) {
		return obj != null && 
				(((Person)obj).getFirstName().equals(this.getFirstName()) && ((Person)obj).getLastName().equals(this.getLastName()) && ((Person)obj).getBirthDate().equals(this.getBirthDate()));
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName + " " + birthDate;
	}
	
}
	
	

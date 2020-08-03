package com.example.lavanya.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lavanya.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	Optional<Person> findById(int id);
	

}

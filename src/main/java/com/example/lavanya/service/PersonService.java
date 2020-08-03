package com.example.lavanya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lavanya.model.Person;
import com.example.lavanya.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getPersonsList() {
		return personRepository.findAll();
	}
	
	public void save(Person person) {
		personRepository.save(person);
	}
	
	
	public Person getPersonById(int id) {
		
		Person person = personRepository.findById(id).get();
		
		return person;
	}
	
	
}

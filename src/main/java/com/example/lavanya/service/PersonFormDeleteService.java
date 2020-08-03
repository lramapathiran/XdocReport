package com.example.lavanya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lavanya.model.PersonFormDelete;
import com.example.lavanya.repository.PersonFormDeleteRepository;

@Service
public class PersonFormDeleteService {
	
	@Autowired
	PersonFormDeleteRepository personFormDeleteRepository;
	
	public void deletePerson(PersonFormDelete personFormDelete ) {
		
		personFormDeleteRepository.delete(personFormDelete);
		
	}

}

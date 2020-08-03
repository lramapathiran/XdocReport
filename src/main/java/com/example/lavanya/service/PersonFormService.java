package com.example.lavanya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lavanya.model.PersonForm;
import com.example.lavanya.repository.PersonFormRepository;

@Service
public class PersonFormService {
	
	@Autowired
	private PersonFormRepository personFormRepository;
	
	
	
	public void save(PersonForm personForm) {
		personFormRepository.save(personForm);
	}

	
		

}

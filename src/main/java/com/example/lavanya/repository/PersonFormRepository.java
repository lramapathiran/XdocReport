package com.example.lavanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lavanya.model.PersonForm;

@Repository
public interface PersonFormRepository extends JpaRepository<PersonForm, Integer> {

}

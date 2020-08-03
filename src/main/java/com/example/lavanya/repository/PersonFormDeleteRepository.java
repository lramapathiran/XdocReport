package com.example.lavanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lavanya.model.PersonFormDelete;

@Repository
public interface PersonFormDeleteRepository extends JpaRepository<PersonFormDelete, Integer> {

}

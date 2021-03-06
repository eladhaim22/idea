package com.uade.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Person;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
	 
	List<Person> findByDniIn(List<String> dni);

}

package com.uade.idea.repository;

import com.uade.idea.domain.Person;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}

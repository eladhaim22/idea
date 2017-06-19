package com.uade.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.State;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface StateRepository extends JpaRepository<State, Long> {

}

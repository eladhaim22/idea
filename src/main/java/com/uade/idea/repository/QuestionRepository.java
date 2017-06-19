package com.uade.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Question;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}

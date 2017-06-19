package com.uade.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Answer;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}

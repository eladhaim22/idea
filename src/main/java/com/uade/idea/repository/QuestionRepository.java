package com.uade.idea.repository;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.Question;
import com.uade.idea.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}

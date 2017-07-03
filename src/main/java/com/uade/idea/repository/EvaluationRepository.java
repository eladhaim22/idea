package com.uade.idea.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Evaluation;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
	List<Evaluation> findAllByProjectId(Long projectId);
	
	List<Evaluation> findAllByCreatedBy(String login);
}

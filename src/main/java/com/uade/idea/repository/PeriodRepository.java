package com.uade.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.Period;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface PeriodRepository extends JpaRepository<Period, Long> {

}

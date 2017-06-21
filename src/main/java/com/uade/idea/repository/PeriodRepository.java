package com.uade.idea.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.Period;
import com.uade.idea.domain.User;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface PeriodRepository extends JpaRepository<Period, Long> {
	 Period findOneByActiveIsTrue();
}

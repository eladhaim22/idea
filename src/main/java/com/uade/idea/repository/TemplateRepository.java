package com.uade.idea.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.idea.domain.Template;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface TemplateRepository extends JpaRepository<Template, Long> {
	
	Optional<Template> findOneByName(String name);
}

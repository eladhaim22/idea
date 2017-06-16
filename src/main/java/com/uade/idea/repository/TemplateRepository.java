package com.uade.idea.repository;

import com.uade.idea.domain.Project;
import com.uade.idea.domain.Template;
import com.uade.idea.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface TemplateRepository extends JpaRepository<Template, Long> {
	
	Optional<Template> findOneByName(String name);
}

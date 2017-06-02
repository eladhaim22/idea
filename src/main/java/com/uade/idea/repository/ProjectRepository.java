package com.uade.idea.repository;

import com.uade.idea.domain.Project;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}

package com.uade.idea.repository;

import com.uade.idea.domain.Project;
import com.uade.idea.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<User> findall(List<Long> userIds); 

}

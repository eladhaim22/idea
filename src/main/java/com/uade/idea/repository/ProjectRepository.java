package com.uade.idea.repository;

import com.uade.idea.domain.Project;
import com.uade.idea.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query(value = "SELECT * FROM Projects p JOIN Users_Projects up on p.id = up.project_id"  
			+ " JOIN Users u on u.id = up.user_id  WHERE u.id = :userId", nativeQuery=true)
	List<Project> findByUserId(@Param("userId") Long userId); 
}

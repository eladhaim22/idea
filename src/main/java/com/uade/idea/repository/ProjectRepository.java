package com.uade.idea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.uade.idea.domain.Project;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query(value = "SELECT * FROM Projects p JOIN Users_Projects up on p.id = up.project_id"  
			+ " JOIN Users u on u.id = up.user_id  WHERE u.id = :userId", nativeQuery=true)
	List<Project> findByUserId(@Param("userId") Long userId); 
	
	@Query(value = "SELECT * FROM Projects p JOIN States s on p.id = s.project_id where s.active = 1 and s.status = 3", nativeQuery=true)
	List<Project> findByQualifiedProject();
}

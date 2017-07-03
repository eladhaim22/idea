package com.uade.idea.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.uade.idea.domain.Project;
import com.uade.idea.domain.Status;
import com.uade.idea.domain.User;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<Project> findAllByUsersId(Long userId); 
	
	List<Project> findAllByStatesActiveTrueAndUsersIdAndStatesStatusLike(Long userId,Status state);
	
    @EntityGraph(attributePaths = "states")
    List<Project> findAllByStatesActiveTrueAndStatesStatusLike(Status status);
}

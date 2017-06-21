package com.uade.idea.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uade.idea.domain.Project;

/**
 * Spring Data JPA repository for the Authority entity.
 */
/*public class ProjectRepositoryImpl<Project, Long extends Serializable>
	extends SimpleJpaRepository<Project, ID> implements Project {
	
	private EntityManager entityManager;
	
	// There are two constructors to choose from, either can be used.
	public MyRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
	  super(domainClass, entityManager);
	
	  // This is the recommended method for accessing inherited class dependencies.
	  this.entityManager = entityManager;
	}
	
	public void sharedCustomMethod(ID id) {
	  // implementation goes here
	}
}*/
package com.uade.idea.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.idea.config.Constants;

import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.User;
import com.uade.idea.service.mapper.PersonMapper;
import com.uade.idea.service.mapper.ProjectMapper;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class ProjectDTO {

	private Long id;
	        
	private String title;
	    
    private Set<PersonDTO> team;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProjectDTO() {
        // Empty constructor needed for Jackson.
    }

    public ProjectDTO(Project project) {
        this(project.getId(), project.getTitle(),project.getCreatedBy(),project.getCreatedDate(), 
        	project.getLastModifiedBy(), project.getLastModifiedDate(),
        	PersonMapper.INSTANCE.personToPersonDTOs(project.getTeam()));
    }
    
    public ProjectDTO(Long id, String title,
        String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
        Set<PersonDTO> team) {

        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle(){
    	return title;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PersonDTO> getTeam() {
        return team;
    }

}

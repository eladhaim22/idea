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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters.PeriodToStringConverter;

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

	public void setTitle(String title) {
		this.title = title;
	}

	private Long id;
	        
	private String title;
	    
    private Set<PersonDTO> team;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<Long> usersIds;

    private Set<StateDTO> states;
    
	@Autowired
    private PersonMapper personMapper;
    
    public ProjectDTO() {
        // Empty constructor needed for Jackson.
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
    
    public void setTeam(Set<PersonDTO> person){
    	this.team = person;
    }
    
    public Set<Long> getUsersIds() {
		return usersIds;
	}

	public void setUsersIds(Set<Long> usersIds) {
		this.usersIds = usersIds;
	}

	public Set<StateDTO> getStates() {
		return states;
	}

	public void setStates(Set<StateDTO> states) {
		this.states = states;
	}
	
}

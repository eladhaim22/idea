package com.uade.idea.service.dto;

import java.time.Instant;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.idea.service.mapper.PersonMapper;

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
    
    private Set<AnswerDTO> answers;
    
    private Set<Long> evaluationsIds;
    
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
	
    public Set<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnsewrs(Set<AnswerDTO> answers) {
		this.answers = answers;
	}

	public void setEvaluationsIds(Set<Long> evaluationsIds) {
		this.evaluationsIds = evaluationsIds;
	}

	public Set<Long> getEvaluationsIds() {
		return evaluationsIds;
	}
		
}

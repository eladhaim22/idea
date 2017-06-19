package com.uade.idea.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.http.impl.client.AIMDBackoffManager;
import org.hibernate.annotations.Filter;

import com.sun.istack.NotNull;
import com.uade.idea.domain.AbstractAuditingEntity;

public class EvaluationDTO extends AbstractAuditingEntity{
	
	private Long id;
	
    private Set<AnswerDTO> answers = new HashSet<>();

    private Long projectId;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<AnswerDTO> answers) {
		this.answers = answers;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}

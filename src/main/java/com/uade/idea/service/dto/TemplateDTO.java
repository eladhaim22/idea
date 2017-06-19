package com.uade.idea.service.dto;

import java.util.List;

/**
 * A DTO representing a user, with his authorities.
 */
public class TemplateDTO {

	private Long id;
	        
	private String name;
	    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	private List<QuestionDTO> questions;

    
}

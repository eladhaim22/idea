package com.uade.idea.service.dto;


import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.domain.Stage;

/**
 * A DTO representing a user, with his authorities.
 */
public class PersonUadeDTO extends PersonDTO{

	private String fileNumber;
    
   	private String career;
    
    private Stage stage;
	
    public PersonUadeDTO() {
        // Empty constructor needed for Jackson.
    } 

    @JsonProperty
	public String getFileNumber() {
		return fileNumber;
	}

    @JsonSetter
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}

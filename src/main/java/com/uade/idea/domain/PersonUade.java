package com.uade.idea.domain;

import com.uade.idea.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;




/**
 * A user.
 */
@Entity
@DiscriminatorValue("personUade")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonUade extends Person {    

	private static final long serialVersionUID = 1L;
	
	@NotNull
    @Column(name = "filenumber")
    private String fileNumber;
    
   	@NotNull
    @Column(name = "carrer")
    private String career;
    
    @NotNull
    @Column(name = "stage")
    @Enumerated
    private Stage stage;
   
    public String getFileNumber() {
		return fileNumber;
	}

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


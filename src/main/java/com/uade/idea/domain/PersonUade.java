package com.uade.idea.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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


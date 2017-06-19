package com.uade.idea.service.dto;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = PersonDTO.class, name = "person"),
        @JsonSubTypes.Type(value = PersonUadeDTO.class, name = "personUade")})
public class PersonDTO {

	private Long id;

    private String firstName;
    
    private String lastName;
    
    private String dni;
    
    private String phoneNumber;
    
    private String email;
    
    private int age;
    
    private Set<Integer> projectsIds;

    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setProjectsIds(Set<Integer> projectsIds) {
		this.projectsIds = projectsIds;
	}

    public PersonDTO() {
        // Empty constructor needed for Jackson.
    } 
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDni() {
		return dni;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public Set<Integer> getProjectsIds() {
		return projectsIds;
	}
}

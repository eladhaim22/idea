package com.uade.idea.service.dto;


import java.util.Set;
import java.util.stream.Collectors;

import com.uade.idea.domain.Person;

/**
 * A DTO representing a user, with his authorities.
 */
public class PersonDTO {

    private Long id;

    private String firstName;
    
    private String lastName;
    
    private String dni;
    
    private String phoneNumber;
    
    private String email;
    
    private int age;
    
    private Set<Integer> projectsIds;

    public PersonDTO() {
        // Empty constructor needed for Jackson.
    } 

    public PersonDTO(Person person) {
        this(person.getId(), person.getFirstName(), person.getLastName(),
            person.getEmail(),person.getAge(),person.getDni(),person.getPhoneNumber(),
            person.getProjects().stream().map(project -> project.getId().intValue())
            .collect(Collectors.toSet()));
    }

    public PersonDTO(Long id, String firstName, String lastName,
        String email,int age,String dni,String phoneNumber,Set<Integer> projectsIds) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.projectsIds = projectsIds;
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

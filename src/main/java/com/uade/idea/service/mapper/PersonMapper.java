 package com.uade.idea.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.domain.Stage;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.PersonUadeDTO;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class PersonMapper {
	
	@Autowired
	private PersonRepository personRepository;
	
    public Person ToModel(PersonDTO personDTO){
    	Person person;
    	if(personDTO.getId() != null){
    		person = personRepository.getOne(personDTO.getId());
    	}
    	else if(personDTO instanceof PersonUadeDTO){
    		person = new PersonUade();
    	}
    	else {
    		person = new Person();
    	}
    	if(personDTO instanceof PersonUadeDTO){
	        person.setFirstName( personDTO.getFirstName() );
	        person.setLastName( personDTO.getLastName() );
	        person.setEmail( personDTO.getEmail() );
	        person.setPhoneNumber( personDTO.getPhoneNumber() );
	        person.setDni( personDTO.getDni() );
	        person.setAge( personDTO.getAge() );
    		((PersonUade)person).setFileNumber(((PersonUadeDTO) personDTO).getFileNumber());
    		((PersonUade)person).setCareer(((PersonUadeDTO) personDTO).getCareer());
    		((PersonUade)person).setStage(Stage.values()[((PersonUadeDTO) personDTO).getStage()]);
    	}
    	else{
	        person.setFirstName( personDTO.getFirstName() );
	        person.setLastName( personDTO.getLastName() );
	        person.setEmail( personDTO.getEmail() );
	        person.setPhoneNumber( personDTO.getPhoneNumber() );
	        person.setDni( personDTO.getDni() );
	        person.setAge( personDTO.getAge() );
    	}
    	return person;
    }
    
    public PersonDTO ToDTO(Person person){
    	if(person instanceof PersonUade){
    		PersonUadeDTO personUadeDTO = new PersonUadeDTO();
    		personUadeDTO.setId( person.getId() );
	        personUadeDTO.setFirstName( person.getFirstName() );
	        personUadeDTO.setLastName( person.getLastName() );
	        personUadeDTO.setEmail( person.getEmail() );
	        personUadeDTO.setPhoneNumber( person.getPhoneNumber() );
	        personUadeDTO.setDni( person.getDni() );
	        personUadeDTO.setAge( person.getAge() );
    		personUadeDTO.setFileNumber(((PersonUade) person).getFileNumber());
			personUadeDTO.setCareer(((PersonUade) person).getCareer());
			personUadeDTO.setStage(((PersonUade) person).getStage().ordinal());
	        return personUadeDTO;
    	}
    	else{
    		PersonDTO personDTO = new PersonDTO();
	        personDTO.setId( person.getId() );
	        personDTO.setFirstName( person.getFirstName() );
	        personDTO.setLastName( person.getLastName() );
	        personDTO.setEmail( person.getEmail() );
	        personDTO.setPhoneNumber( person.getPhoneNumber() );
	        personDTO.setDni( person.getDni() );
	        personDTO.setAge( person.getAge() );
	        return personDTO;
    	}
    }
}

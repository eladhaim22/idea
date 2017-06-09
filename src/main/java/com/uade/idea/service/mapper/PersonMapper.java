 package com.uade.idea.service.mapper;

import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.PersonUadeDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class PersonMapper {
	    
    public Person ToModel(PersonDTO personDTO){
    	if(personDTO instanceof PersonUadeDTO){
    		PersonUade personUade = new PersonUade();
    		personUade.setId( personDTO.getId() );
	        personUade.setFirstName( personDTO.getFirstName() );
	        personUade.setLastName( personDTO.getLastName() );
	        personUade.setEmail( personDTO.getEmail() );
	        personUade.setPhoneNumber( personDTO.getPhoneNumber() );
	        personUade.setDni( personDTO.getDni() );
	        personUade.setAge( personDTO.getAge() );
    		personUade.setFileNumber(((PersonUadeDTO) personDTO).getFileNumber());
			personUade.setCareer(((PersonUadeDTO) personDTO).getCareer());
			personUade.setStage(((PersonUadeDTO) personDTO).getStage());
	        return personUade;
    	}
    	else{
    		Person person = new Person();
	        person.setId( personDTO.getId() );
	        person.setFirstName( personDTO.getFirstName() );
	        person.setLastName( personDTO.getLastName() );
	        person.setEmail( personDTO.getEmail() );
	        person.setPhoneNumber( personDTO.getPhoneNumber() );
	        person.setDni( personDTO.getDni() );
	        person.setAge( personDTO.getAge() );
	        return person;
    	}
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
			personUadeDTO.setStage(((PersonUade) person).getStage());
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

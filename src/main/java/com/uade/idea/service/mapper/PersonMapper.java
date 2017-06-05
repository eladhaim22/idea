package com.uade.idea.service.mapper;

import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.PersonUadeDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public abstract class PersonMapper {
	
	public abstract PersonDTO personToPersonDto(Person person);

    public abstract List<PersonDTO> personToPersonDTOs(List<Person> person);
    
    public Person personDTOToPerson(PersonDTO personDTO){
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
    
    public abstract List<Person> personDTOsToPerson(List<PersonDTO> personDTOs);

    public Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}

package com.uade.idea.service.mapper;

import com.uade.idea.domain.Person;
import com.uade.idea.service.dto.PersonDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {
	
	default PersonDTO personToPersonDto(Person person) {
        return new PersonDTO(person);
    }

    List<PersonDTO> personToPersonDTOs(List<Person> person);
    
    Person personDTOToPerson(PersonDTO personDTO);

    List<Person> personDTOsToPerson(List<PersonDTO> personDTOs);

    default Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}

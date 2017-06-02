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

	PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );
	
	default PersonDTO personToPersonDto(Person person) {
        return new PersonDTO(person);
    }

    Set<PersonDTO> personToPersonDTOs(Set<Person> person);

    Person personDTOToPerson(PersonDTO personDTO);

    Set<Person> personDTOsToPerson(Set<PersonDTO> personDTOs);

    default Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}

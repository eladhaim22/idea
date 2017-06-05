package com.uade.idea.service.mapper;

import com.uade.idea.domain.PersonUade;
import com.uade.idea.service.dto.PersonUadeDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public interface PersonUadeMapper {
	
	PersonUadeMapper INSTANCE = Mappers.getMapper( PersonUadeMapper.class );
	
	default PersonUadeDTO PersonUadeToPersonUadeDto(PersonUade PersonUade) {
        return new PersonUadeDTO(PersonUade);
    }

    List<PersonUadeDTO> PersonUadeToPersonUadeDTOs(List<PersonUade> PersonUade);
    
    PersonUade PersonUadeDTOToPersonUade(PersonUadeDTO PersonUadeDTO);

    List<PersonUade> PersonUadeDTOsToPersonUade(List<PersonUadeDTO> PersonUadeDTOs);

    default PersonUade PersonUadeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonUade PersonUade = new PersonUade();
        PersonUade.setId(id);
        return PersonUade;
    }
}

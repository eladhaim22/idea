package com.uade.idea.service.mapper;

import com.google.common.collect.Lists;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.User;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.UserDTO;


import com.google.common.collect.Sets;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

	@Autowired
	private PersonMapper personMapper;
    
	
	public ProjectDTO projectToProjectDTO(Project project) {
        return new ProjectDTO(project);
    }

   	public abstract List<ProjectDTO> ProjectsToProjectDTOs(List<Project> project);

	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	public abstract Project projectDTOToProject(ProjectDTO projectDTO);

    public abstract List<Project> projectDTOsToProjects(List<ProjectDTO> projectDTOs);

    public Set<Person> map(Set<PersonDTO> value){
    	return Sets.newHashSet((personMapper.personDTOsToPerson(value.stream().collect(Collectors.toList()))));
    }
    
    /*default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }*/
}

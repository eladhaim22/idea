package com.uade.idea.service.mapper;

import com.uade.idea.domain.Project;
import com.uade.idea.service.dto.ProjectDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {

	ProjectMapper INSTANCE = Mappers.getMapper( ProjectMapper.class );
	
    default ProjectDTO projectToProjectDTO(Project project) {
        return new ProjectDTO(project);
    }

   Set<ProjectDTO> ProjectsToProjectDTOs(Set<Project> project);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Project projectDTOToProject(ProjectDTO projectDTO);

    Set<Project> projectDTOsToProjects(Set<ProjectDTO> projectDTOs);

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

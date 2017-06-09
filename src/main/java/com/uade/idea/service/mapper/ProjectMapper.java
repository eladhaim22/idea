package com.uade.idea.service.mapper;

import com.google.common.collect.Lists;
import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.State;
import com.uade.idea.domain.User;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.repository.UserRepository;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.dto.UserDTO;


import com.google.common.collect.Sets;
import com.sun.jersey.spi.inject.Inject;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class ProjectMapper {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public ProjectDTO ToDTO(Project source){
		ProjectDTO projectDto = new ProjectDTO();
		projectDto.setId(source.getId());
		projectDto.setTitle(source.getTitle());
		projectDto.setLastModifiedDate(source.getLastModifiedDate());
		projectDto.setTeam(source.getTeam().stream().map(person -> personMapper.ToDTO(person)).collect(Collectors.toSet()));
		projectDto.setUsersIds(source.getUsers().stream().map(user -> user.getId()).collect(Collectors.toSet()));
		projectDto.setStates(source.getStates().stream().map(state -> StateToDto(state)).collect(Collectors.toSet()));
		return projectDto;
	}
	
	public Project ToModel(ProjectDTO source){
		Project project = new Project();
		project.setId(source.getId());
		project.setLastModifiedDate(source.getLastModifiedDate());
		project.setTeam(source.getTeam().stream().map(personDto -> personMapper.ToModel(personDto)).collect(Collectors.toSet()));
		project.setUsers(Sets.newHashSet(userRepository.findByIdIn(source.getUsersIds().stream().collect(Collectors.toList()))));
		project.setTitle(source.getTitle());
		project.setStates(source.getStates().stream().map(state -> StateToModel(state)).collect(Collectors.toSet()));
		return project;
	}
	
	private StateDTO StateToDto(State source){
		StateDTO stateDto = new StateDTO();
		stateDto.setActive(source.isActive());
		stateDto.setStatus(source.getStatus());
		stateDto.setId(source.getId());
		return stateDto;
	}
	
	private State StateToModel(StateDTO source){
		State state = new State();
		state.setActive(source.isActive());
		state.setStatus(source.getStatus());
		state.setId(source.getId());
		return state;
	}
	
}

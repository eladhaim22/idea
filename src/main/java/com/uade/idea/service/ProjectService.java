package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.Status;
import com.uade.idea.domain.User;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.mapper.ProjectMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class for managing projects.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private UserService userService;
    
   
    /*public ProjectService(ProjectRepository projectRepository,UserService userService,ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.projectMapper = projectMapper;
    }*/
    
    public void CreateProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	Set<Long> usersIds = new HashSet<Long>();
    	usersIds.add(userService.getUserWithAuthorities().getId());
    	projectDTO.setUsersIds(usersIds);
    	StateDTO stateDto = new StateDTO();
    	stateDto.setActive(true);
    	stateDto.setStatus(Status.Initial);
    	Set<StateDTO> states = new HashSet<>();
    	states.add(stateDto);
    	projectDTO.setStates(states);
    	Project project = projectMapper.ToModel(projectDTO);
    	projectRepository.save(project);
    }
    
    
    public Set<ProjectDTO> GetAll(){
    	log.debug("Getting all projects");
    	Set<Project> projects = Sets.newHashSet(projectRepository.findAll());
    	return projects.stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet());
    }
    
    public ProjectDTO GetById(long id){
    	User user = userService.getUserWithAuthorities();
    	Project project = projectRepository.getOne(id);
    	if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.ADMIN))){
    		log.debug("Getting project with id:" + id);
    		return projectMapper.ToDTO(project);
    	}
    	else {
    		if(user.getAuthorities().stream().anyMatch(authoritiy -> authoritiy.getName().toString() == AuthoritiesConstants.REFERRE.toString()) &&
    				project.getUsers().stream().anyMatch(u -> u.getId() == user.getId())){
    			log.debug("Getting project with id:" + id);
    			return projectMapper.ToDTO(project);
    		}
    		else { 
    			if(project.getCreatedBy() == user.getLogin()){
    				return projectMapper.ToDTO(project);
    			}
    		}
    	}
    	throw new SecurityException("The user can't get this project");	
    }
    
    
    public Set<ProjectDTO> projectsByAuthority(){
    	User user = userService.getUserWithAuthorities();
    	Project project = projectRepository.
    	if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.ADMIN))){
    		log.debug("Getting project with id:" + id);
    		return projectMapper.ToDTO(project);
    	}
    	else {
    		if(user.getAuthorities().stream().anyMatch(authoritiy -> authoritiy.getName().toString() == AuthoritiesConstants.REFERRE.toString()) &&
    				project.getUsers().stream().anyMatch(u -> u.getId() == user.getId())){
    			log.debug("Getting project with id:" + id);
    			return projectMapper.ToDTO(project);
    		}
    		else { 
    			if(project.getCreatedBy() == user.getLogin()){
    				return projectMapper.ToDTO(project);
    			}
    		}
    	}
    	throw new SecurityException("The user can't get this project");	
    }
}

package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.mapper.ProjectMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class for managing projects.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    
    private final ProjectRepository projectRepository;
    
   
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    public void SaveProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	projectRepository.save(ProjectMapper.INSTANCE.projectDTOToProject(projectDTO));
    }
    
    public Set<ProjectDTO> GetAll(){
    	log.debug("Getting all projects");
    	return ProjectMapper.INSTANCE.ProjectsToProjectDTOs(Sets.newHashSet(projectRepository.findAll()));

    }

}

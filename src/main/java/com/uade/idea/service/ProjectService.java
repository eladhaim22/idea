package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Project;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.mapper.ProjectMapper;

import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired    
    private ProjectMapper projectMapper;
 
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    public void SaveProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	Project project = projectMapper.projectDTOToProject(projectDTO);
    	projectRepository.save(project);
    }
    
    public Set<ProjectDTO> GetAll(){
    	log.debug("Getting all projects");
    	return Sets.newHashSet(projectMapper.ProjectsToProjectDTOs(projectRepository.findAll()));
    }
    public ProjectDTO GetById(long id){
    	log.debug("Getting project with id:" + id);
    	return projectMapper.projectToProjectDTO(projectRepository.getOne(id));
    }

}

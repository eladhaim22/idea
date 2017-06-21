package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Evaluation;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.State;
import com.uade.idea.domain.Status;
import com.uade.idea.domain.User;
import com.uade.idea.repository.EvaluationRepository;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.dto.EvaluationDTO;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.mapper.EvaluationMapper;
import com.uade.idea.service.mapper.PersonMapper;
import com.uade.idea.service.mapper.ProjectMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
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
public class EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationService.class);
    
    @Autowired 
    private EvaluationRepository evaluationRepository;
    
    @Autowired
    private EvaluationMapper evaluationMapper;
    
    @Autowired 
    private ProjectRepository projectRepository;
    
    @Autowired 
    private UserService userService;
    
    public void CreateEvaluation(EvaluationDTO evaluationDTO){
    	User user = userService.getUserWithAuthorities();
    	Project project = projectRepository.getOne(evaluationDTO.getProjectId());
    	if(project.getUsers().stream().anyMatch(u -> u.getId() == user.getId()) && evaluationDTO.getId() == null){
    		log.debug("Saving evaluation to project {}:", evaluationDTO.getProjectId());
        	Evaluation evaluation = new Evaluation();
        	evaluation = evaluationMapper.ToModel(evaluationDTO);
        	//project.getEvaluations().add(evaluation);
        	evaluationRepository.save(evaluation);
        	Set<User> referres = project.getUsers().stream().filter(u -> u.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.REFERRE))).collect(Collectors.toSet());
        	if(referres.stream().anyMatch(referre -> project.getEvaluations().stream().anyMatch(e -> e.getCreatedBy().equals(referre.getLogin())))){
        		project.getStates().forEach(state -> state.setActive(false));
        		State state = new State();
            	state.setActive(true);
            	state.setStatus(Status.FinalStage);
        		project.getStates().add(state);
        	}		
        	projectRepository.save(project);
    	}
    	else {
    		throw new SecurityException("The referee is not assigned for this project");
    	}
    }
    
    public EvaluationDTO GetById(Long id){
    	log.debug("Getting evaluation by id:{}",id);
    	return evaluationMapper.ToDTO(evaluationRepository.getOne(id));
    }
    
    public Set<EvaluationDTO> GetByProjectId(long id){
    	log.debug("Getting evaluations by project id:{}",id);
    	return evaluationRepository.findAllByProjectId(id).stream().map(ev -> evaluationMapper.ToDTO(ev)).collect(Collectors.toSet());
    }
    
}

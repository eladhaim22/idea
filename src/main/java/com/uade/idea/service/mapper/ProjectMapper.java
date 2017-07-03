package com.uade.idea.service.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uade.idea.domain.Project;
import com.uade.idea.repository.EvaluationRepository;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.repository.UserRepository;
import com.uade.idea.service.dto.ProjectDTO;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class ProjectMapper {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private AnswerMapper answerMapper;
	
	@Autowired 
	private PersonRepository personRepository;
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired
	private StateMapper stateMapper;
	
	@Autowired
	private EvaluationRepository evaluationRepository;
	
	public Project ToModel(ProjectDTO source){
		Project project;
		if(source.getId() != null){
			project = projectRepository.findOne(source.getId());
			project.getEvaluations().clear();
			project.getEvaluations().addAll(evaluationRepository.findAllByProjectId(project.getId()));
		}
		else{
			project = new Project();
		}
	
		project.setLastModifiedDate(source.getLastModifiedDate());
		project.getTeam().clear();
		project.getTeam().addAll(personRepository.findByDniIn(source.getTeam().stream().map(personDto -> personDto.getDni()).collect(Collectors.toList())).stream().collect(Collectors.toSet()));
		project.getUsers().clear();
		project.getUsers().addAll(userRepository.findByIdIn(source.getUsersIds().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet()));
		project.setTitle(source.getTitle());
		project.getStates().clear();
		project.getStates().addAll(source.getStates().stream().map(state -> stateMapper.ToModel(state)).collect(Collectors.toSet()));
		project.getAnswers().clear();
		project.getAnswers().addAll(source.getAnswers().stream().map(answerDto -> answerMapper.ToModel(answerDto)).collect(Collectors.toSet()));
		project.setComment(source.getComment());
		return project;
	}
	
	public ProjectDTO ToDTO(Project source){
		ProjectDTO projectDto = new ProjectDTO();
		projectDto.setId(source.getId());
		projectDto.setTitle(source.getTitle());
		projectDto.setLastModifiedDate(source.getLastModifiedDate());
		projectDto.setCreatedDate(source.getCreatedDate());
		projectDto.setTeam(source.getTeam().stream().map(person -> personMapper.ToDTO(person)).collect(Collectors.toSet()));
		projectDto.setUsersIds(source.getUsers().stream().map(user -> user.getId()).collect(Collectors.toSet()));
		projectDto.setStates(source.getStates().stream().map(state -> stateMapper.ToDto(state)).collect(Collectors.toSet()));
		projectDto.setAnsewrs(source.getAnswers().stream().map(answer -> answerMapper.ToDTO(answer)).collect(Collectors.toSet()));
		projectDto.setEvaluationsIds(source.getEvaluations().stream().map(evaluation -> evaluation.getId()).collect(Collectors.toSet()));
		projectDto.setComment(source.getComment());
		return projectDto;
	}	
}

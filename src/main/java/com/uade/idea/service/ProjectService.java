package com.uade.idea.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Period;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.Status;
import com.uade.idea.domain.User;
import com.uade.idea.repository.PeriodRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.RankingDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.mapper.ProjectMapper;


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
    
    @Autowired 
    private PersonService personService;
    
    @Autowired 
    private PeriodRepository periodRepository;
 
    public void CreateProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	Period period = periodRepository.findOneByActiveIsTrue();
    	if(period.getStartingDate().before(Calendar.getInstance().getTime()) && period.getEndingDate().after(Calendar.getInstance().getTime())){
	    	Set<Long> usersIds = new HashSet<Long>();
	    	usersIds.add(userService.getUserWithAuthorities().getId());
	    	projectDTO.setUsersIds(usersIds);
	    	StateDTO stateDto = new StateDTO();
	    	stateDto.setActive(true);
	    	stateDto.setStatus(Status.Initial);
	    	Set<StateDTO> states = new HashSet<>();
	    	states.add(stateDto);
	    	projectDTO.setStates(states);
	    	personService.saveAndUpdateUsers(projectDTO.getTeam().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
	    	Project project = projectMapper.ToModel(projectDTO);
	    	project.setPeriod(period);
	    	projectRepository.save(project);
    	}
    	else
    		throw new ServiceException("La convoctoria no esta abierta todavia");
    }
    
    public void SaveProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	personService.saveAndUpdateUsers(projectDTO.getTeam().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
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
    	Project project = projectRepository.findOne(id);
    	if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.ADMIN))){
    		log.debug("Getting project with id:" + id);
    		return projectMapper.ToDTO(project);
    	}
    	else {
    		if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.REFERRE)) &&
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
    
    
    public Set<ProjectDTO> projectsByUser(){
    	User user = userService.getUserWithAuthorities();
    	if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.ADMIN))){
    		log.debug("Getting all projects for {0}:",user.getLogin());
    		return Sets.newHashSet(projectRepository.findAll()).stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet());
    	}
    	else {
    		return Sets.newConcurrentHashSet(projectRepository.findByUserId(user.getId()).stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet()));
    	}
    }
    
    public List<RankingDTO> GetQualifiedProject(){
    	 List<Project> projects = new ArrayList<Project>(); 
    	 projects = projectRepository.findByQualifiedProject();
    	 List<RankingDTO> ranking = new ArrayList<RankingDTO>();
    	 for(Project project : projects){
    		 RankingDTO r = new RankingDTO();
    		 r.setProject(projectMapper.ToDTO(project));
    		 r.setAverage(project.getEvaluations().stream().mapToDouble(e -> e.getAnswers().stream().mapToDouble(a -> Double.parseDouble(a.getQuestionAnswer())).sum()).sum() / ((double)project.getEvaluations().stream().findFirst().get().getAnswers().size()));
    		 ranking.add(r);
    	 }
    	 return ranking;
    }
}

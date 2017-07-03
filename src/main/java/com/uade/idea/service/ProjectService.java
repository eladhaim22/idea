package com.uade.idea.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.microsoft.windowsazure.exception.ServiceException;
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

import io.github.jhipster.config.JHipsterProperties.Http;


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
    
    @PersistenceContext
    private EntityManager entityManager;
 
    @Autowired
    private HttpServletRequest request;
    
    private Session getSession(){
    	return  this.entityManager.unwrap(Session.class);
    }
    
    public void CreateProject(ProjectDTO projectDTO) throws ServiceException{
    	log.debug("Saving project:", projectDTO.getTitle());
    	Period period = periodRepository.findOneByActiveIsTrue();
    	if(Calendar.getInstance().getTime().after(period.getStartingDate()) && Calendar.getInstance().getTime().before(period.getEndingDate())){
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
    		 throw new ServiceException("Todavia no se abrio la convocatoria");
    }
    
    public void SaveProject(ProjectDTO projectDTO){
    	log.debug("Saving project:", projectDTO.getTitle());
    	personService.saveAndUpdateUsers(projectDTO.getTeam().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
    	Project project = projectMapper.ToModel(projectDTO);
    	projectRepository.save(project);
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
    			if(project.getCreatedBy().compareTo(user.getLogin()) == 0){
    				return projectMapper.ToDTO(project);
    			}
    		}
    	}
    	throw new SecurityException("The user can't get this project");	
    }
    
    
    public Set<ProjectDTO> projectsByUser(){
    	this.setSessionFilter();
    	User user = userService.getUserWithAuthorities();
    	if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.ADMIN))){
    		log.debug("Getting all projects for {0}:",user.getLogin());
    		return Sets.newHashSet(projectRepository.findAll()).stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet());
    	}
    	else {
    		if(user.getAuthorities().stream().anyMatch(auth -> new String(auth.getName()).equals(AuthoritiesConstants.REFERRE))){
    			return Sets.newConcurrentHashSet(projectRepository.findAllByStatesActiveTrueAndUsersIdAndStatesStatusLike(user.getId(),Status.valueOf("PreSelected"))
    					.stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet()));
    		}
    		else{
    			return Sets.newConcurrentHashSet(projectRepository.findAllByUsersId(user.getId()).stream().map(project -> projectMapper.ToDTO(project)).collect(Collectors.toSet()));
    		}
    	}
    }
    
    public List<RankingDTO> GetQualifiedProject(){
    	 this.setSessionFilter();
    	 List<Project> projects = new ArrayList<Project>();
		 projects = projectRepository.findAllByStatesActiveTrueAndStatesStatusLike(Status.valueOf("FinalStage"));
		 List<RankingDTO> ranking = new ArrayList<RankingDTO>();
		 for(Project project : projects){
			 RankingDTO r = new RankingDTO();
			 r.setProject(projectMapper.ToDTO(project));
			 r.setAverage(project.getEvaluations().stream().mapToDouble(e -> e.getAnswers().stream().mapToDouble(a -> Double.parseDouble(a.getQuestionAnswer())).sum()).sum() /
					 ((double)project.getEvaluations().stream().findFirst().get().getAnswers().size()) /  ((double)project.getEvaluations().size()));
			 ranking.add(r);
		 }
		 return ranking;
    }
    
    private void setSessionFilter(){	
		if(request.getHeader("Period_Id") != null){
			this.getSession().enableFilter("getProjectsByPeriod").setParameter("period_id",Long.parseLong(request.getHeader("Period_Id")));
			return;
		}
    	this.getSession().enableFilter("getProjectsOfActivePeriod");
    }
}

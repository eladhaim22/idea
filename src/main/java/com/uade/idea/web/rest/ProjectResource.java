package com.uade.idea.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uade.idea.domain.Status;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.ProjectService;
import com.uade.idea.service.UserService;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.ProjectIdAndListOfReferres;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.dto.UserDTO;
import com.uade.idea.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import javax.websocket.MessageHandler.Partial;

/**
 * REST controller for managing users.
 *
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
@RequestMapping("/api/project")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "projectManagement";

    private final ProjectService projectService;
    
    private final UserService userService;
    
    public ProjectResource(ProjectService projectService,UserService userService) {
    	this.projectService = projectService;
    	this.userService = userService;
    }

    @PostMapping("")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity createProject(@RequestBody ProjectDTO projectDto) throws URISyntaxException {
        log.debug("REST request to save project : {}", projectDto.getTitle());
        projectService.CreateProject(projectDto);
        return ResponseEntity.created(new URI("/api/project/"))
                .headers(HeaderUtil.createAlert( "el projecto se creo",null)).build();
    }
    
    @PostMapping("/changeState")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity changeState(@RequestBody ProjectIdAndListOfReferres projectIdAndListOfUsers) throws URISyntaxException {
        log.debug("REST request to change project {0} to state {1}", projectIdAndListOfUsers.getProjectId());
        ProjectDTO projectDto = projectService.GetById(projectIdAndListOfUsers.getProjectId());
        if(projectDto.getStates().stream().anyMatch(projectState -> projectState.isActive() && projectState.getStatus() == Status.Initial && projectIdAndListOfUsers.getStatus().equals(Status.PreSelected))){
        	projectDto.getStates().stream().forEach(projectState -> projectState.setActive(false));
        	StateDTO s = new StateDTO();
        	s.setActive(true);
        	s.setStatus(Status.PreSelected);
        	projectDto.getStates().add(s);
        	projectDto.setUsersIds(projectIdAndListOfUsers.getUsers());
        	projectService.SaveProject(projectDto);
        }
        else if(projectDto.getStates().stream().anyMatch(projectState -> projectState.isActive() && projectState.getStatus() == Status.Initial && projectIdAndListOfUsers.getStatus().equals(Status.Rejected))){
        	projectDto.getStates().stream().forEach(projectState -> projectState.setActive(false));
        	StateDTO s = new StateDTO();
        	s.setActive(true);
        	s.setStatus(Status.Rejected);
        	projectDto.getStates().add(s);
        	projectService.SaveProject(projectDto);
        }
        
        return ResponseEntity.created(new URI("/api/project/"))
                .headers(HeaderUtil.createAlert( "el projecto esta preseleccionado",null)).build();
    }
    
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getById(@PathVariable("id") String id) throws URISyntaxException{
        try{ 
	    	log.debug("REST request project with id  : {}", id);	        
	        ProjectDTO projectDTO = projectService.GetById(Long.parseLong(id));
	        return new ResponseEntity<>(projectDTO,HttpStatus.OK);
        }
        catch(SecurityException ex){
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("")
    @Timed
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getAllByAuthority() throws URISyntaxException{
    	try{ 
	    	log.debug("REST request project by authority");
	    	Set<ProjectDTO> projects = projectService.projectsByUser();
	    	return new ResponseEntity<>(projects, null, HttpStatus.OK);
    	} 
	    catch(SecurityException ex){
         	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         }
    } 
}

package com.uade.idea.web.rest;

import com.uade.idea.config.Constants;
import com.codahale.metrics.annotation.Timed;
import com.uade.idea.domain.User;
import com.uade.idea.repository.UserRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.MailService;
import com.uade.idea.service.ProjectService;
import com.uade.idea.service.UserService;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.UserDTO;
import com.uade.idea.web.rest.vm.ManagedUserVM;
import com.uade.idea.web.rest.util.HeaderUtil;
import com.uade.idea.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "projectManagement";

    private final ProjectService projectService;
    
    public ProjectResource(ProjectService projectService) {
    	this.projectService = projectService;
    }

    @PostMapping("/project")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity saveProject(@RequestBody ProjectDTO projectDto) throws URISyntaxException {
        log.debug("REST request to save project : {}", projectDto.getTitle());
        projectService.SaveProject(projectDto);
        return ResponseEntity.created(new URI("/api/project/"))
                .headers(HeaderUtil.createAlert( "el projecto se creo",null)).build();
    }
    
    @GetMapping("/project")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity getAll() throws URISyntaxException{
        log.debug("REST request all projects : {}");
        Set<ProjectDTO> projects = projectService.GetAll();
        return new ResponseEntity<>(projects, null, HttpStatus.OK);
    }
    
    @GetMapping("/project/{id}")
    @Timed
    public ResponseEntity getById(@PathVariable("id") String id) throws URISyntaxException{
        log.debug("REST request project with id  : {}", id);
        ProjectDTO projectDTO = projectService.GetById(Long.parseLong(id));
        return new ResponseEntity<>(projectDTO, null, HttpStatus.OK);
    }

}

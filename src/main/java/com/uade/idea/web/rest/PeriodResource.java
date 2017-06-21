package com.uade.idea.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.uade.idea.domain.Status;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.EvaluationService;
import com.uade.idea.service.ProjectService;
import com.uade.idea.service.TemplateService;
import com.uade.idea.service.UserService;
import com.uade.idea.service.dto.EvaluationDTO;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.ProjectIdAndListOfReferres;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.dto.TemplateDTO;
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
@RequestMapping("/api/evaluation")
public class PeriodResource {

    private final Logger log = LoggerFactory.getLogger(PeriodResource.class);

    private final EvaluationService evaluationService;
        
    public PeriodResource(EvaluationService evaluationService) {
    	this.evaluationService = evaluationService;
    }

    @GetMapping("/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN,AuthoritiesConstants.REFERRE})
    public ResponseEntity GetById(@PathVariable("id") Long id) throws URISyntaxException{
	    	log.debug("REST request evaluation by id  : {}", id);	        
	        EvaluationDTO evaluationDTO = evaluationService.GetById(id);
	        return new ResponseEntity<>(evaluationDTO,HttpStatus.OK);
    }
    
    @GetMapping("/ByProjectId/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity GetByProjectId(@PathVariable("id") Long id) throws URISyntaxException{
	    	log.debug("REST request evaluation by project id  : {}", id);	        
	        Set<EvaluationDTO> evaluationsDTO = evaluationService.GetByProjectId(id);
	        return new ResponseEntity<>(evaluationsDTO,HttpStatus.OK);
    }
    
    @PostMapping("/")
    @Timed
    @Secured(AuthoritiesConstants.REFERRE)
    public ResponseEntity CreateEvaluation(@RequestBody EvaluationDTO evaluationDto) throws URISyntaxException {
        try{
    	log.debug("REST request to save evaluation by referre : {}", evaluationDto.getCreatedBy());
        evaluationService.CreateEvaluation(evaluationDto);
        return ResponseEntity.created(new URI("/api/evaliation/"))
                .headers(HeaderUtil.createAlert( "la evaluacion se creo",null)).build();
        }
        catch(SecurityException ex){
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    
    
  
}

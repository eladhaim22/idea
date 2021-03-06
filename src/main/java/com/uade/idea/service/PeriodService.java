package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Evaluation;
import com.uade.idea.domain.Period;
import com.uade.idea.domain.Project;
import com.uade.idea.domain.State;
import com.uade.idea.domain.Status;
import com.uade.idea.domain.User;
import com.uade.idea.repository.EvaluationRepository;
import com.uade.idea.repository.PeriodRepository;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.service.dto.EvaluationDTO;
import com.uade.idea.service.dto.PeriodDTO;
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.mapper.EvaluationMapper;
import com.uade.idea.service.mapper.PeriodMapper;
import com.uade.idea.service.mapper.PersonMapper;
import com.uade.idea.service.mapper.ProjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
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
public class PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);
    
    @Autowired 
    private PeriodRepository periodRepository;
    
    @Autowired
    private PeriodMapper periodMapper;
    
    public void CreatePeriod(PeriodDTO periodDTO){
    	Period period = periodMapper.ToModel(periodDTO);
    	period.setActive(false);
    	periodRepository.save(period);
    }
    
    public void ActivatePeriod(Long periodId){
    	Period previousPeriod = new Period();
    	previousPeriod = periodRepository.findOneByActiveIsTrue();
    	if(previousPeriod != null){
    		throw new ServiceException("Debe cerrar el periodo anterior antes de activar otro");
    	}
    	else{
    		Period period = periodRepository.getOne(periodId);
    		period.setActive(true);
    		periodRepository.save(period);
    	}	
    }
    
    public void DeactivatePeriod(Long periodId){
    	Period period = periodRepository.getOne(periodId);
    	period.setActive(false);
    	periodRepository.save(period);
    }
    
    public List<PeriodDTO> GetAll(){
    	log.debug("Getting all periods");
    	List<Period> periods = periodRepository.findAll();
    	return periods.stream().map(period -> periodMapper.ToDTO(period)).collect(Collectors.toList());
    }
    
    public PeriodDTO GetById(Long id){
    	log.debug("Getting period by id:{}",id);
    	return periodMapper.ToDTO(periodRepository.getOne(id));
    }
}

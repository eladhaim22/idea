package com.uade.idea.service;

import com.google.common.collect.Sets;
import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Evaluation;
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
import com.uade.idea.service.dto.ProjectDTO;
import com.uade.idea.service.dto.StateDTO;
import com.uade.idea.service.mapper.EvaluationMapper;
import com.uade.idea.service.mapper.PeriodMapper;
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
public class PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);
    
    @Autowired 
    private PeriodRepository periodRepository;
    
    @Autowired
    private PeriodMapper periodMapper;
    
}

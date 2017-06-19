package com.uade.idea.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.idea.repository.TemplateRepository;
import com.uade.idea.service.dto.TemplateDTO;
import com.uade.idea.service.mapper.TemplateMapper;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    public TemplateDTO GetByName(String name){
    	log.debug("Getting template with name {}:",name);
		return templateMapper.ToDTO(templateRepository.findOneByName(name).get());
    }
    
}

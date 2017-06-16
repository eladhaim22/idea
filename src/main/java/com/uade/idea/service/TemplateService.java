package com.uade.idea.service;

import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.User;
import com.uade.idea.repository.AuthorityRepository;
import com.uade.idea.repository.PersistentTokenRepository;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.repository.TemplateRepository;
import com.google.common.collect.Sets;
import com.uade.idea.config.Constants;
import com.uade.idea.repository.UserRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.security.SecurityUtils;
import com.uade.idea.service.util.RandomUtil;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.TemplateDTO;
import com.uade.idea.service.dto.UserDTO;
import com.uade.idea.service.mapper.PersonMapper;
import com.uade.idea.service.mapper.TemplateMapper;
import com.uade.idea.service.mapper.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

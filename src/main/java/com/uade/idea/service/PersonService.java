package com.uade.idea.service;

import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.User;
import com.uade.idea.repository.AuthorityRepository;
import com.uade.idea.repository.PersistentTokenRepository;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.config.Constants;
import com.uade.idea.repository.UserRepository;
import com.uade.idea.security.AuthoritiesConstants;
import com.uade.idea.security.SecurityUtils;
import com.uade.idea.service.util.RandomUtil;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.UserDTO;
import com.uade.idea.service.mapper.PersonMapper;
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
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<Person> saveAndUpdateUsers(List<PersonDTO> personDto){
    	List<Person> existingPerson = personRepository.findByDniIn(personDto.stream().map(persondto -> persondto.getDni()).collect(Collectors.toList()));
    	List<Person> personOnProject = personDto.stream().map(persondto -> personMapper.ToModel(persondto)).collect(Collectors.toList());
    	personOnProject.forEach(pop -> {
    		Optional<Person> p = existingPerson.stream().filter(ep -> ep.getDni() == pop.getDni()).findFirst();
    		if(p.isPresent()){
    			pop.setDni(p.get().getDni());
    		}
    	});
    	personRepository.save(personOnProject);
    	return personOnProject;
    }
}

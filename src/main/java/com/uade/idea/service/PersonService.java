package com.uade.idea.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.idea.domain.Person;
import com.uade.idea.repository.PersonRepository;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.mapper.PersonMapper;

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

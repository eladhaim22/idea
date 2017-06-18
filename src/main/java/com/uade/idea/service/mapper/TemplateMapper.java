package com.uade.idea.service.mapper;

import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.domain.Question;
import com.uade.idea.domain.Template;
import com.uade.idea.domain.User;
import com.uade.idea.repository.TemplateRepository;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.PersonUadeDTO;
import com.uade.idea.service.dto.QuestionDTO;
import com.uade.idea.service.dto.TemplateDTO;
import com.uade.idea.service.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class TemplateMapper {

	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private QuestionMapper questionMapper;
	
    public Template ToModel(TemplateDTO templateDTO){
    	Template template;
    	if(templateDTO.getId() != null){
    		template = templateRepository.getOne(templateDTO.getId());
    	}
    	else {
    		template = new Template();
    	}
    	template.setName(template.getName());
    	template.getQuestions().clear();
    	template.getQuestions().addAll(templateDTO.getQuestions().stream().map(question -> questionMapper.ToModel(question)).collect(Collectors.toList()));
    	return template;
    }
    
    public TemplateDTO ToDTO(Template template){
    	TemplateDTO templateDTO = new TemplateDTO();
    	templateDTO.setId(template.getId());
    	templateDTO.setName(template.getName());
    	templateDTO.setQuestions(template.getQuestions().stream().map(question -> questionMapper.ToDTO(question)).collect(Collectors.toList()));
    	return templateDTO;
    }
}

package com.uade.idea.service.mapper;

import com.uade.idea.domain.Authority;
import com.uade.idea.domain.Person;
import com.uade.idea.domain.PersonUade;
import com.uade.idea.domain.Question;
import com.uade.idea.domain.Template;
import com.uade.idea.domain.User;
import com.uade.idea.service.dto.PersonDTO;
import com.uade.idea.service.dto.PersonUadeDTO;
import com.uade.idea.service.dto.QuestionDTO;
import com.uade.idea.service.dto.TemplateDTO;
import com.uade.idea.service.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class TemplateMapper {

    public Template ToModel(TemplateDTO templateDTO){
    	Template template = new Template(); 
    	template.setId(templateDTO.getId());
    	template.setName(template.getName());
    	template.setQuestions(templateDTO.getQuestions().stream().map(question -> QuestionToModel(question)).collect(Collectors.toList()));
    	return template;
    }
    
    public TemplateDTO ToDTO(Template template){
    	TemplateDTO templateDTO = new TemplateDTO();
    	templateDTO.setId(template.getId());
    	templateDTO.setName(template.getName());
    	templateDTO.setQuestions(template.getQuestions().stream().map(question -> QuestionToDTO(question)).collect(Collectors.toList()));
    	return templateDTO;
    }
    
    private QuestionDTO QuestionToDTO(Question question){
    	QuestionDTO questionDto = new QuestionDTO();
    	questionDto.setId(question.getId());
    	questionDto.setSection(question.getSection());
    	questionDto.setSubsection(question.getSubsection());
    	questionDto.setDetail(question.getDetail());
    	return questionDto;
    }
    
    private Question QuestionToModel(QuestionDTO questionDto){
    	Question question = new Question();
    	question.setId(questionDto.getId());
    	question.setSection(questionDto.getSection());
    	question.setSubsection(questionDto.getSubsection());
    	question.setDetail(questionDto.getDetail());
    	return question;
    }
}

package com.uade.idea.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uade.idea.domain.Question;
import com.uade.idea.repository.QuestionRepository;
import com.uade.idea.service.dto.QuestionDTO;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class QuestionMapper {

	@Autowired
	private QuestionRepository questionRepository;
 
	 public Question ToModel(QuestionDTO questionDto){
    	Question question;
    	if(questionDto.getId() != null){
    		question = questionRepository.getOne(questionDto.getId());
    	}
    	else {
    		question = new Question();
    	}
    	
    	question.setSection(questionDto.getSection());
    	question.setSubsection(questionDto.getSubsection());
    	question.setDetail(questionDto.getDetail());
    	return question;
	 }
	 
    public QuestionDTO ToDTO(Question question){
    	QuestionDTO questionDto = new QuestionDTO();
    	questionDto.setId(question.getId());
    	questionDto.setSection(question.getSection());
    	questionDto.setSubsection(question.getSubsection());
    	questionDto.setDetail(question.getDetail());
    	return questionDto;
    }
 
}

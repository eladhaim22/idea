 package com.uade.idea.service.mapper;

import com.uade.idea.domain.Answer;
import com.uade.idea.repository.QuestionRepository;
import com.uade.idea.service.dto.AnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class AnswerMapper {
	
	@Autowired
	private QuestionRepository questionRepository;
    
	public Answer ToModel(AnswerDTO answerDTO){
		Answer answer = new Answer();
		answer.setId(answerDTO.getId());
		answer.setQuestion((questionRepository.getOne(answerDTO.getQuestionId())));
		answer.setQuestionAnswer(answerDTO.getQuestionAnswer());
		return answer;
	}
    
    public AnswerDTO ToDTO(Answer answer){
    	AnswerDTO answerDto = new AnswerDTO();
    	answerDto.setId(answer.getId());
    	answerDto.setQuestionId(answer.getQuestion().getId());
    	answerDto.setQuestionAnswer(answer.getQuestionAnswer());
    	return answerDto;
    }
}

 package com.uade.idea.service.mapper;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.State;
import com.uade.idea.repository.AnswerRepository;
import com.uade.idea.repository.QuestionRepository;
import com.uade.idea.repository.StateRepository;
import com.uade.idea.service.dto.AnswerDTO;
import com.uade.idea.service.dto.StateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class StateMapper {
	
	@Autowired
	private StateRepository stateRepository;
	
	public State ToModel(StateDTO source){
		State state;
		if(source.getId() != null){
			state = stateRepository.getOne(source.getId());
		}
		else {
			state = new State();
		}
		
		state.setActive(source.isActive());
		state.setStatus(source.getStatus());
		return state;
	}
	
	public StateDTO ToDto(State source){
		StateDTO stateDto = new StateDTO();
		stateDto.setActive(source.isActive());
		stateDto.setStatus(source.getStatus());
		stateDto.setId(source.getId());
		return stateDto;
	}

}

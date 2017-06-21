 package com.uade.idea.service.mapper;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.Period;
import com.uade.idea.repository.AnswerRepository;
import com.uade.idea.repository.PeriodRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.repository.QuestionRepository;
import com.uade.idea.service.dto.AnswerDTO;
import com.uade.idea.service.dto.PeriodDTO;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class PeriodMapper {

	@Autowired
	private PeriodRepository periodRepository;
	
	public Period ToModel(PeriodDTO periodDTO){
		Period period;
		if(periodDTO.getId() != null){
			period = periodRepository.getOne(periodDTO.getId());
		}
		else{
			period = new Period();
		}
		period.setStartingDate(periodDTO.getStartingDate());
		period.setPresentionLimitDate(periodDTO.getPresentionLimitDate());
		period.setEndingDate(periodDTO.getEndingDate());
		return period;
	}
    
    public PeriodDTO ToDTO(Period period){
    	PeriodDTO periodDto = new PeriodDTO();
    	periodDto.setId(period.getId());
    	periodDto.setStartingDate(period.getStartingDate());
    	periodDto.setPresentionLimitDate(period.getPresentionLimitDate());
    	periodDto.setEndingDate(period.getEndingDate());
    	periodDto.setProjects(period.getProjects().stream().map(project -> project.getId()).collect(Collectors.toList()));
    	return periodDto;
    }
}

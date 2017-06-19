 package com.uade.idea.service.mapper;

import com.uade.idea.domain.Answer;
import com.uade.idea.domain.Evaluation;
import com.uade.idea.repository.AnswerRepository;
import com.uade.idea.repository.EvaluationRepository;
import com.uade.idea.repository.ProjectRepository;
import com.uade.idea.repository.QuestionRepository;
import com.uade.idea.service.dto.AnswerDTO;
import com.uade.idea.service.dto.EvaluationDTO;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class EvaluationMapper {
	
	@Autowired
	private EvaluationRepository evaluationRepository;
    
	@Autowired
	private ProjectRepository projectRepository;
    
	@Autowired 
	private AnswerMapper answerMapper;
	
	public Evaluation ToModel(EvaluationDTO evaluationDTO){
		Evaluation evaluation;
		if(evaluationDTO.getId() != null){
			evaluation = evaluationRepository.getOne(evaluationDTO.getId());
		}
		else{
			evaluation = new Evaluation();
		}
		evaluation.getAnswers().clear();
		evaluation.getAnswers().addAll(evaluationDTO.getAnswers().stream().map(answer -> answerMapper.ToModel(answer)).collect(Collectors.toSet()));
		evaluation.setProject(projectRepository.getOne(evaluationDTO.getProjectId()));
		return evaluation;
	}
    
    public EvaluationDTO ToDTO(Evaluation evaluation){
    	EvaluationDTO evaluationDto = new EvaluationDTO();
    	evaluationDto.setId(evaluation.getId());
    	evaluationDto.setProjectId(evaluation.getProject().getId());
    	evaluationDto.setAnswers(evaluation.getAnswers().stream().map(answer -> answerMapper.ToDTO(answer)).collect(Collectors.toSet()));
    	return evaluationDto;
    }
}

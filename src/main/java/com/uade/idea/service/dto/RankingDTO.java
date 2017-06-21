package com.uade.idea.service.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RankingDTO {

	private ProjectDTO project;
	private Double average;
	
	public RankingDTO(){}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		BigDecimal bd = new BigDecimal(average);
		bd = bd.setScale(3, RoundingMode.HALF_UP);
		this.average = bd.doubleValue();
	}
	
}

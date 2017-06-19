package com.uade.idea.service.dto;

import java.text.DecimalFormat;

public class RankingDTO {

	private ProjectDTO project;
	private double average;
	
	public RankingDTO(){}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		DecimalFormat dtime = new DecimalFormat("#.###"); 
		this.average = Double.valueOf(dtime.format(average));
	}
	
}

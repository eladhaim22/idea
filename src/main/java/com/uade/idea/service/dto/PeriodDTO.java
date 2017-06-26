package com.uade.idea.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeriodDTO {
	
    private Long id;
    private List<Long> projects = new ArrayList<>();
    private Date startingDate;
    private Date presentionLimitDate;
    private Date endingDate;
    private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getProjects() {
		return projects;
	}

	public void setProjects(List<Long> projects) {
		this.projects = projects;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getPresentionLimitDate() {
		return presentionLimitDate;
	}

	public void setPresentionLimitDate(Date presentionLimitDate) {
		this.presentionLimitDate = presentionLimitDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
    
	public void setActive(boolean active){
		this.active = active;
	}
	
	public boolean getActive(){
		return this.active;
	}
}


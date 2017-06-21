package com.uade.idea.service.dto;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uade.idea.domain.Status;


public class ProjectIdAndListOfReferres
{
	private Long projectId;
	private Set<Long> users;
	private Status status;
	
	public ProjectIdAndListOfReferres(){};
    	
	public Long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Set<Long> getUsers() {
		return users;
	}
	
	public void setUsers(Set<Long> users) {
		this.users = users;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
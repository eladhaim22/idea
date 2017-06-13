package com.uade.idea.service.dto;

import java.util.Set;


public class ProjectIdAndListOfReferres
{
	private Long projectId;
	private Set<Long> users;
	
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
}
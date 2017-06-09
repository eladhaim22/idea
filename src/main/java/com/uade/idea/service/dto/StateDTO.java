package com.uade.idea.service.dto;

import com.uade.idea.domain.AbstractAuditingEntity;
import com.uade.idea.domain.Status;

public class StateDTO extends AbstractAuditingEntity {

	private Long id;
	
	private Status status;
	
	private boolean active;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

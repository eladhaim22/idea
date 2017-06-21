package com.uade.idea.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

@Entity
@Table(name="Periods")
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    @JoinColumn(name="period_id", nullable = false, insertable = false, updatable = false)
    private List<Project> projects = new ArrayList<>();
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date presentionLimitDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endingDate;

    @Column(name="active")
	@NotNull
    private boolean active;
    
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public List<Project> getProjects() {
		return projects;
	}

	protected void setProjects(List<Project> projects) {
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
    
}
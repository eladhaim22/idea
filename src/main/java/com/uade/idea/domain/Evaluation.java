package com.uade.idea.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.http.impl.client.AIMDBackoffManager;
import org.hibernate.annotations.Filter;

import com.sun.istack.NotNull;

@Entity
@Table(name="Evaluations")
public class Evaluation extends AbstractAuditingEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="evaluation_id", nullable=false)
    @Filter(name="answers",condition="evaluation_id is not null")
	private Set<Answer> answers = new HashSet<>();

    @OneToOne
    @JoinColumn(name="project_id")
    private Project project;
    
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
	
	public Set<Answer> getAnswers() {
		return answers;
	}

	protected void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}

package com.uade.idea.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="evaluations")
public class Evaluation extends AbstractAuditingEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="evaluation_id", nullable=true)
    @NotNull
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="project_id")
    @NotNull
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

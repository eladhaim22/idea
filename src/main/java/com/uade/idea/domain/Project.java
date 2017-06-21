package com.uade.idea.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "projects")
// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "period_id", nullable = false)
	private Period period;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "person_projects", joinColumns = {
			@JoinColumn(name = "project_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "person_id", referencedColumnName = "id") })
	// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@BatchSize(size = 20)
	private Set<Person> team = new HashSet<>();;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "users_projects", joinColumns = {
			@JoinColumn(name = "project_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") })
	// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@BatchSize(size = 20)
	private Set<User> users = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id", nullable = false)
	private Set<State> states = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id", nullable = false)
	//@Filter(name = "answers", condition = "evaluation_id is null")
	private Set<Answer> answers = new HashSet<>();

	@OneToMany
	@JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
	private Set<Evaluation> evaluations = new HashSet<>();

	@OneToMany
	public Set<User> getUsers() {
		return users;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	protected void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public Set<State> getStates() {
		return states;
	}

	protected void setStates(Set<State> states) {
		this.states = states;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Person> getTeam() {
		return team;
	}

	protected void setTeam(Set<Person> team) {
		this.team = team;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	protected void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

}
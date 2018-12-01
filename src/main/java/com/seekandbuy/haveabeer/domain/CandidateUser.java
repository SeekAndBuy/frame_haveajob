package com.seekandbuy.haveabeer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="CandidateUser")
public class CandidateUser extends User {

	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	private JobCharacteristic jobCharacteristic;
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@Column(unique = true)
	private String cpf;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public JobCharacteristic getJobCharacteristic() {
		return jobCharacteristic;
	}
	
	public void setJobCharacteristic(JobCharacteristic jobCharacteristic) {
		this.jobCharacteristic = jobCharacteristic;
	}
	
}

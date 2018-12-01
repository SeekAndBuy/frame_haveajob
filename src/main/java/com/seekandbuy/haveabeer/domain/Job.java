package com.seekandbuy.haveabeer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name="Job")
public class Job extends Product {
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	private JobCharacteristic jobCharacteristic;
	
	public JobCharacteristic getJobCharacteristic() {
		return jobCharacteristic;
	}
	
	public void setJobCharacteristic(JobCharacteristic jobCharacteristic) {
		this.jobCharacteristic = jobCharacteristic;
	}
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	private EmployerUser user;
	
	public EmployerUser getUser() {
		return user;
	}

	public void setUser(EmployerUser user) {
		this.user = user;
	}
}

package com.seekandbuy.haveajob.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="EmployerUser")
public class EmployerUser extends User {

	/*
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@ManyToOne
	JobCharacteristic beerCharacteristic;
	*/
	
	@JsonInclude(Include.NON_NULL)
	@Cascade(CascadeType.PERSIST)
	@Column(unique = true)
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	/*
	public JobCharacteristic getBeerCharacteristic() {
		return beerCharacteristic;
	}
	
	public void setBeerCharacteristic(JobCharacteristic beerCharacteristic) {
		this.beerCharacteristic = beerCharacteristic;
	}
	*/
	
}

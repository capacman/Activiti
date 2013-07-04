package com.globalmaksimum.bpmn.banking.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Basic
	private int numberOfKMH;
	@Basic
	private boolean hasOpenCredit;
	@Basic
	private boolean finansMass;
	@Basic
	private String name;
	@Basic
	private String surname;
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSalaryPayment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumberOfKMH() {
		return numberOfKMH;
	}

	public void setNumberOfKMH(int numberOfKMH) {
		this.numberOfKMH = numberOfKMH;
	}

	public boolean isHasOpenCredit() {
		return hasOpenCredit;
	}

	public void setHasOpenCredit(boolean hasOpenCredit) {
		this.hasOpenCredit = hasOpenCredit;
	}

	public boolean isFinansMass() {
		return finansMass;
	}

	public void setFinansMass(boolean finansMass) {
		this.finansMass = finansMass;
	}

	public Date getLastSalaryPayment() {
		return lastSalaryPayment;
	}

	public void setLastSalaryPayment(Date lastSalaryPayment) {
		this.lastSalaryPayment = lastSalaryPayment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}

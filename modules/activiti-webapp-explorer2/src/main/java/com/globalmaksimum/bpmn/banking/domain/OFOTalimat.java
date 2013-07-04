package com.globalmaksimum.bpmn.banking.domain;

import java.io.Serializable;

public class OFOTalimat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -688238304212321579L;
	private int id = 0;

	public OFOTalimat(int id) {
		super();
		this.id = id;
	}
	public OFOTalimat() {
		super();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OFOTalimat [id=" + id + "]";
	}

}

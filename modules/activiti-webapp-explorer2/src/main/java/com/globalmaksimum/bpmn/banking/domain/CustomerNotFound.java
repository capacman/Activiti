package com.globalmaksimum.bpmn.banking.domain;


public class CustomerNotFound extends FinansPocException {

	public CustomerNotFound(Long id) {
		super("customer " + id + " not found");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6588260939313603987L;

}

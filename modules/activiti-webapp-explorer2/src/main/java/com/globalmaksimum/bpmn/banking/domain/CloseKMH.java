package com.globalmaksimum.bpmn.banking.domain;

public class CloseKMH {

	private long kmhAccountID;
	private boolean enforce;

	public CloseKMH(long kmhAccountID, boolean enforce) {
		super();
		this.kmhAccountID = kmhAccountID;
		this.enforce = enforce;
	}

	public CloseKMH() {
	}

	public long getKmhAccountID() {
		return kmhAccountID;
	}

	public void setKmhAccountID(long kmhAccountID) {
		this.kmhAccountID = kmhAccountID;
	}

	public boolean isEnforce() {
		return enforce;
	}

	public void setEnforce(boolean enforce) {
		this.enforce = enforce;
	}
}

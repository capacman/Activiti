package com.globalmaksimum.bpmn.banking.domain;


public class CampaignNotStarted extends FinansPocException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1602311715865955666L;

	public CampaignNotStarted(String campaignName, Long customerID) {
		super("campaign with id " + campaignName + " for customer "
				+ customerID + " not started");
	}

}

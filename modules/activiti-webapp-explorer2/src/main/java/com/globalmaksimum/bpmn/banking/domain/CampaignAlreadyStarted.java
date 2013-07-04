package com.globalmaksimum.bpmn.banking.domain;


public class CampaignAlreadyStarted extends FinansPocException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613207022225572114L;

	public CampaignAlreadyStarted(String campaignName, Long customerID) {
		super("campaign with id " + campaignName + " for customer "
				+ customerID + " already started");
	}

}

package com.globalmaksimum.bpmn.banking.service;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public interface KmhService {
	public void closeKMH(DelegateExecution execution,CampaignApplicant applicant, String kmh);
}

package com.globalmaksimum.bpmn.banking.delegates;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public class FinansMaasTaskDelegate extends AbstractBankingTaskDelegate {

	@Override
	protected void doExecute(DelegateExecution execution,
			CampaignApplicant applicant) {
		applicant.setFinansMaasCustomer(getCustomer(applicant).isFinansMass());
	}

}

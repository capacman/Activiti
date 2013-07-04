package com.globalmaksimum.bpmn.banking.delegates;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public class KmhTaskDelegate extends AbstractBankingTaskDelegate {

	@Override
	protected void doExecute(DelegateExecution execution,
			CampaignApplicant applicant) {
		applicant.setHasKMH(getCustomer(applicant).getNumberOfKMH() > 0);
	}

}

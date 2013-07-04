package com.globalmaksimum.bpmn.banking.delegates;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public class CreditTaskDelegate extends AbstractBankingTaskDelegate {

	@Override
	protected void doExecute(DelegateExecution execution,
			CampaignApplicant applicant) {
		applicant.setHasOpenCredit(getCustomer(applicant).isHasOpenCredit());
	}

}

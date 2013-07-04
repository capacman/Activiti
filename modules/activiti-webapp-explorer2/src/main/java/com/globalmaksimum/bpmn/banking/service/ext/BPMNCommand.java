package com.globalmaksimum.bpmn.banking.service.ext;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public interface BPMNCommand {

	void executeMessageCheck(DelegateExecution execution, Object messageValue,
			CampaignApplicant applicant);

}

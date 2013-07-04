package com.globalmaksimum.bpmn.banking.service.ext;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;

public class CommandExecuter implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	public void executeMessageCheck(DelegateExecution execution,
			String targetService, Object messageValue,
			CampaignApplicant applicant) {
		BPMNCommand command = applicationContext.getBean(targetService
				+ "commnadBean", BPMNCommand.class);
		command.executeMessageCheck(execution, messageValue,applicant);
		execution.setVariable("messageValue", null);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}
}

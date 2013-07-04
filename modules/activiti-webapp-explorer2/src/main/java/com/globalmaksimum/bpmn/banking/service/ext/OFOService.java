package com.globalmaksimum.bpmn.banking.service.ext;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;
import com.globalmaksimum.bpmn.banking.domain.OFOTalimat;

public class OFOService implements BPMNCommand {
	private static Logger LOGGER = LoggerFactory.getLogger(OFOService.class);

	@Override
	public void executeMessageCheck(DelegateExecution execution,
			Object messageValue, CampaignApplicant applicant) {
		OFOTalimat talimat = (OFOTalimat) messageValue;
		System.out.println("ofoService execution with talimat " + talimat);
		System.out.println("ofoService customerid "
				+ execution.getVariable("customerid"));
		if (talimat.getId() % 2 == 1) {
			System.out.println("ofoService talimat is new");
			execution.setVariable("historyCond", true);
			incrementCount(execution);
		} else {
			execution.setVariable("historyCond", false);
			System.out.println("ofoService talimat is old");
		}
	}

	private void incrementCount(DelegateExecution execution) {
		execution.setVariable("count", getCount(execution) + 1l);
	}

	private long getCount(DelegateExecution execution) {
		return execution.getVariable("count") == null ? 0l
				: (Long) execution.getVariable("count");
	}
}

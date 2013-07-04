package com.globalmaksimum.bpmn.banking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;

import com.globalmaksimum.bpmn.banking.domain.CampaignAlreadyStarted;
import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;
import com.globalmaksimum.bpmn.banking.domain.CampaignNotStarted;
import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;
import com.globalmaksimum.bpmn.banking.domain.OFOTalimat;

public class CampaignServiceImpl implements CampaignService {

	private CustomerRepository customerRepository;
	private HistoryService historyService;
	private RuntimeService runtimeService;

	@Override
	public ProcessInstance startCampaign(String campaignName, Long customerID) {
		return startCampaign(campaignName, customerID, null);
	}

	private String getBusinessKey(String campaign, Long customerID) {
		return customerID.toString();
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void setProcessEngine(ProcessEngine engine) {
		this.historyService = engine.getHistoryService();
		this.runtimeService = engine.getRuntimeService();
	}

	@Override
	public boolean triggerMessage(String campaignName, Long customerID,
			String message, String value) {
		Execution execution = runtimeService
				.createExecutionQuery()
				.processInstanceBusinessKey(
						getBusinessKey(campaignName, customerID))
				.processDefinitionKey(campaignName)
				.messageEventSubscriptionName(message).singleResult();
		if (execution != null) {
			Map<String, Object> messageValue = new HashMap<String,Object>();
			messageValue.put(message, value);
			runtimeService.messageEventReceived(message, execution.getId(),
					messageValue);
			return true;
		}
		throw new CampaignNotStarted(campaignName, customerID);
	}

	@Override
	public ProcessInstance startCampaign(String campaignName, Long customerID,
			Map<String, Object> vars) {
		Customer customer = customerRepository.findCustomer(customerID);
		HistoricProcessInstance history = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(
						getBusinessKey(campaignName, customerID))
				.processDefinitionKey(campaignName).singleResult();
		if (history == null) {
			CampaignApplicant applicant = new CampaignApplicant();
			applicant.setCustomerid(customerID.toString());
			applicant.setName(customer.getName());
			applicant.setSurname(customer.getSurname());
			applicant.setCreationDate(new Date());
			Map<String, Object> parms = new HashMap<String, Object>(
					vars == null ? new HashMap<String, Object>() : vars);
			parms.put("applicant", applicant);
			parms.put("customerid", customerID.longValue());

			return runtimeService.startProcessInstanceByKey(campaignName,
					getBusinessKey(campaignName, customerID), parms);

		}
		throw new CampaignAlreadyStarted(campaignName, customerID);
	}

	@Override
	public SignalResult triggerGoSignal(Long customerID, String signalName) {
		Execution execution = runtimeService.createExecutionQuery()
				.processInstanceBusinessKey(getBusinessKey("", customerID)).processDefinitionKey("process").activityId(signalName)
				.singleResult();
		if (execution == null)
			return new SignalResult(false, "execution not found for campaign");
		else {
			runtimeService.signal(execution.getId());
			return new SignalResult(true, "signal delivered");
		}
	}

	@Override
	public SignalResult triggerSignal(Long customerID, String name,
			String type, Object value) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("messageType", type);
		vars.put("messageValue", value);
		return triggerSignal(customerID, name, vars);
	}

	private List<Execution> getCustomerExecutions(Long customerID, String name) {
		List<ProcessInstance> processes = runtimeService
				.createProcessInstanceQuery()
				.variableValueEquals("customerid", customerID.longValue())
				.active().list();
		List<Execution> executions = new ArrayList<Execution>();

		for (ProcessInstance instance : processes) {
			executions.addAll(runtimeService.createExecutionQuery()
					.processInstanceId(instance.getId())
					.signalEventSubscriptionName(name).list());
		}
		return executions;
	}

	@Override
	public boolean isfinished(String id) {
		return runtimeService.createProcessInstanceQuery().active()
				.processInstanceId(id).singleResult() == null;
	}

	@Override
	public SignalResult triggerSignal(Long customerID, String name,
			Map<String, Object> vars) {
		List<Execution> executions = getCustomerExecutions(customerID, name);
		if (executions.isEmpty())
			return new SignalResult(false, "execution not found for campaign");
		for (Execution execution : executions) {
			runtimeService.signalEventReceived(name, execution.getId(), vars);
		}
		return new SignalResult(true, "signal delivered");
	}
}

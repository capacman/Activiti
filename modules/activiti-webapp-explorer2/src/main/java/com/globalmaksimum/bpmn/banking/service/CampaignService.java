package com.globalmaksimum.bpmn.banking.service;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

public interface CampaignService {
	public ProcessInstance startCampaign(String campaignName, Long customerID);

	public ProcessInstance startCampaign(String campaignName, Long customerID,
			Map<String, Object> vars);

	public boolean triggerMessage(String campaignName, Long customerID,
			String message, String value);

	public SignalResult triggerGoSignal(Long customerid, String signalName);

	public SignalResult triggerSignal(Long customerID, String name,
			String type, Object value);

	public SignalResult triggerSignal(Long customerID, String name,
			Map<String, Object> vars);

	public boolean isfinished(String id);
}

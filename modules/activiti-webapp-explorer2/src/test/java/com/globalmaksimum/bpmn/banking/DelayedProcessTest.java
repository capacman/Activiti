package com.globalmaksimum.bpmn.banking;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;
import com.globalmaksimum.bpmn.banking.domain.OFOTalimat;
import com.globalmaksimum.bpmn.banking.service.CampaignService;
import com.globalmaksimum.bpmn.banking.service.SignalResult;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DelayedProcessTest extends TestBase {
	private static final String PROCESSNAME = "delayedprocess";
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CampaignService campaignService;

	@Autowired
	ProcessEngine processEngine;

	@Test
	@Ignore
	public void testProcessTeaser() throws InterruptedException {

		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId(), createVariables("PT2S", "", "", 2));
		System.out.println(instance.getId());
		assertNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());

		Thread.sleep(3000);
		assertNotNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());
	}

	@Test
	@Ignore
	public void testProcessWithTeaser() throws InterruptedException {

		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId(),
				createVariables("PT15S", "PT5S", "send sms", 2));
		System.out.println(instance.getId());
		assertNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());

		Thread.sleep(17000);
		assertNotNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());
	}

	@Test
	public void testProcessWithTeaserOFO() throws InterruptedException {

		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId(),
				createVariables("PT16S", "PT5S", "send sms", 2));
		System.out.println(instance.getId());
		assertNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());
		List<Execution> list = processEngine.getRuntimeService().createExecutionQuery()
				.processInstanceId(instance.getId()).signalEventSubscriptionName("extensionMessage").list();
		for (Execution execution : list) {
			System.out.println(execution);
		}
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("messageType", "OFO");
		vars.put("messageValue", new OFOTalimat(1));
		SignalResult signalResult = campaignService.triggerSignal(customer.getId(), "extensionMessage",
				"OFO", new OFOTalimat(1));
		//assertTrue(signalResult.isSuccess());
		processEngine.getRuntimeService().signalEventReceived(
				"extensionMessage", vars);
		Thread.sleep(18000);
		assertNotNull(processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceId(instance.getId()).singleResult());
	}

	private Map<String, Object> createVariables(String delayTime,
			String teaserTime, String teaserText, int count) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("teaserDuration", teaserTime);
		vars.put("duration", delayTime);
		vars.put("minimum", 2);
		vars.put("count", 0);
		vars.put("teaserText", teaserText);
		vars.put("targetService", "OFO");
		return vars;
	}
}

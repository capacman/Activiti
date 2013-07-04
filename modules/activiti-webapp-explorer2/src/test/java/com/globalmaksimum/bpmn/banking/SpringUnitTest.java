package com.globalmaksimum.bpmn.banking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.globalmaksimum.bpmn.banking.domain.CampaignAlreadyStarted;
import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;
import com.globalmaksimum.bpmn.banking.domain.CampaignNotStarted;
import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerNotFound;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;
import com.globalmaksimum.bpmn.banking.domain.OFOTalimat;
import com.globalmaksimum.bpmn.banking.service.CampaignService;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringUnitTest extends TestBase {
	private static final String PROCESSNAME = "process";
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CampaignService campaignService;

	@Autowired
	ProcessEngine processEngine;

	@After
	public void suspend() {
		List<ProcessInstance> list = processEngine.getRuntimeService()
				.createProcessInstanceQuery().active().list();
		for (ProcessInstance processInstance : list) {
			processEngine.getRuntimeService().suspendProcessInstanceById(
					processInstance.getId());
		}
	}

	@Test
	public void testCreateCustomer() {
		int size = customerRepository.listCustomers().size();
		Customer customer = createCustomer(customerRepository);
		createCustomer(customerRepository);
		customerRepository.findCustomer(customer.getId());
		assertEquals(size + 2, customerRepository.listCustomers().size());
	}

	@Test
	public void testCreateCampaign() {
		assertNotNull(processEngine);

		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);

		ProcessInstance result = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult();
		HistoricProcessInstance historicProcessInstance = processEngine
				.getHistoryService().createHistoricProcessInstanceQuery()
				.finished().processInstanceId(instance.getId()).singleResult();
		assertNull(historicProcessInstance);
		assertNotNull(result);
		assertFalse(result.isEnded());
	}

	@Test(expected = CustomerNotFound.class)
	public void testCreateCampaignNonExistentCustomer() {
		assertNotNull(processEngine);
		campaignService.startCampaign(PROCESSNAME, -1L);
	}

	@Test(expected = CampaignAlreadyStarted.class)
	public void testCreateCampaignAlreadyStarted() {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);
		assertFalse(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult().isEnded());
		campaignService.startCampaign(PROCESSNAME, customer.getId());
	}

	@Test(expected = CampaignNotStarted.class)
	public void testCreateCampaignNotStarted() {
		assertNotNull(processEngine);
		campaignService.triggerMessage(PROCESSNAME, -1L, "message", "value");
	}

	@Test
	public void testKMHCampaign() {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository, true, false,
				daysAfter(-10), 0);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);
		assertNull(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult());
	}

	@Test
	public void testKMHCloseVade() throws InterruptedException {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);

		RuntimeService runtimeService = processEngine.getRuntimeService();
		campaignService.triggerGoSignal(customer.getId(), "waitCreditUse");
		assertEquals(3, runtimeService.createProcessInstanceQuery().active()
				.list().size());
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("kmh", "anilkmh");
		vars.put("forceCloseKMH", true);
		campaignService.triggerSignal(customer.getId(),
				"closeKMH", vars);

		Thread.sleep(1000);

		assertNull(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult());
	}

	@Test
	public void testOFONotGivenVade() throws InterruptedException {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);

		RuntimeService runtimeService = processEngine.getRuntimeService();
		campaignService.triggerGoSignal(customer.getId(), "waitCreditUse");
		assertEquals(3, runtimeService.createProcessInstanceQuery().active()
				.list().size());

		Thread.sleep(36000);

		assertNull(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult());
	}

	@Test
	public void testOFOGivenVade() throws InterruptedException {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);

		campaignService.triggerGoSignal(customer.getId(), "waitCreditUse");
		Thread.sleep(9000);

		campaignService.triggerSignal(customer.getId(), "extensionMessage",
				"OFO", new OFOTalimat(1));

		Thread.sleep(9000);
		campaignService.triggerSignal(customer.getId(), "extensionMessage",
				"OFO", new OFOTalimat(1));
		Thread.sleep(20000);
		assertNull(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult());
	}
}

package com.globalmaksimum.bpmn.banking;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.ProcessEngine;
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
public class ReportingTest extends TestBase {
	private static final String PROCESSNAME = "process";
	private static final int CUSTOMER_COUNT = 400;
	private Random random = new Random(System.currentTimeMillis());
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CampaignService campaignService;

	@Autowired
	ProcessEngine processEngine;

	@Test
	public void generateCustomers() throws InterruptedException {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 4000l,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
						CUSTOMER_COUNT));
		try {
			for (int i = 0; i < CUSTOMER_COUNT; i++) {
				CustomerFlowRunner customerFlowRunner = new CustomerFlowRunner(
						createCustomerProb((customerRepository)));
				executor.submit(customerFlowRunner);
			}
		} catch (Exception e) {
			System.out.println("here we areeeeeee");
			e.printStackTrace();
		}
		while (executor.getCompletedTaskCount() < CUSTOMER_COUNT) {
			System.out.println("current completed count "
					+ executor.getCompletedTaskCount());
			Thread.sleep(2000);
		}
	}

	private class CustomerFlowRunner implements Runnable {
		Customer customer;

		public CustomerFlowRunner(Customer customer) {
			super();
			this.customer = customer;
		}

		@Override
		public void run() {
			try {
				customerFlow(customer);
			} catch (Exception e) {
				System.out.println("here we areeee");
				e.printStackTrace();
			}
		}

	}

	public void customerFlow(Customer customer) throws InterruptedException {
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		if (!campaignService.isfinished(instance.getId())) {
			Thread.sleep(random.nextInt(10000));
			SignalResult signalResult = campaignService.triggerGoSignal(
					customer.getId(), "waitCreditUse");
			assertTrue(signalResult.isSuccess());
			while (!campaignService.isfinished(instance.getId())) {
				Thread.sleep(3000 + random.nextInt(2000));
				if (campaignService.isfinished(instance.getId()))
					break;
				switch (random.nextInt(10)) {
				case 0:
				case 1:
				case 2:
					Map<String, Object> vars = new HashMap<String, Object>();
					vars.put("kmh", "anilkmh");
					vars.put("forceCloseKMH", random.nextBoolean());
					try{
					assertTrue(campaignService.triggerSignal(customer.getId(),
							"closeKMH", vars).isSuccess());
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				default:
					try {
						assertTrue(campaignService
								.triggerSignal(
										customer.getId(),
										"extensionMessage",
										"OFO",
										new OFOTalimat(
												random.nextInt(100) < 20 ? 0
														: 1)).isSuccess());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
	}

	@Test
	@Ignore
	public void testOFOGivenVade() throws InterruptedException {
		assertNotNull(processEngine);
		Customer customer = createCustomer(customerRepository);
		ProcessInstance instance = campaignService.startCampaign(PROCESSNAME,
				customer.getId());
		assertNotNull(instance);

		SignalResult signalResult = campaignService.triggerGoSignal(
				customer.getId(), "waitCreditUse");
		assertTrue(signalResult.isSuccess());
		Thread.sleep(9000);

		signalResult = campaignService.triggerSignal(customer.getId(),
				"extensionMessage", "OFO", new OFOTalimat(1));
		assertTrue(signalResult.isSuccess());
		Thread.sleep(9000);
		signalResult = campaignService.triggerSignal(customer.getId(),
				"extensionMessage", "OFO", new OFOTalimat(1));
		assertTrue(signalResult.isSuccess());
		Thread.sleep(20000);
		assertNull(processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(instance.getId()).singleResult());
	}
}

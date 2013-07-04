package com.globalmaksimum.bpmn.banking;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;

public class TestBase {

	Random random = new Random(System.currentTimeMillis());

	public TestBase() {
		super();
	}

	protected Customer createCustomer(CustomerRepository customerRepository) {
		Customer customer = new Customer();
		customer.setFinansMass(true);
		customer.setHasOpenCredit(false);
		customer.setLastSalaryPayment(daysAfter(-10));
		customer.setNumberOfKMH(1);
		customer.setName("Anil");
		customer.setSurname("Halil");
		customerRepository.saveCustomer(customer);
		return customer;
	}
	
	protected Customer createCustomerProb(CustomerRepository customerRepository) {
		Customer customer = new Customer();
		customer.setFinansMass(getBoolean(90, true));
		customer.setHasOpenCredit(getBoolean(5, true));
		customer.setLastSalaryPayment(daysAfter(-random.nextInt(100)));
		customer.setNumberOfKMH(random.nextInt(10));
		customer.setName("Anil");
		customer.setSurname("Halil");
		customerRepository.saveCustomer(customer);
		return customer;
	}

	private boolean getBoolean(int percent, boolean val) {
		return random.nextInt(100) < percent ? val : !val;
	}

	protected Date daysAfter(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	protected Customer createCustomer(CustomerRepository customerRepository,
			boolean finansMaas, boolean hasOpenCredit, Date lastSalaryPayment,
			int numberOfKmh) {
		Customer customer = new Customer();
		customer.setFinansMass(finansMaas);
		customer.setHasOpenCredit(hasOpenCredit);
		customer.setLastSalaryPayment(lastSalaryPayment);
		customer.setNumberOfKMH(numberOfKmh);
		customerRepository.saveCustomer(customer);
		return customer;
	}

}
package com.globalmaksimum.bpmn.banking.service;

import org.activiti.engine.delegate.DelegateExecution;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;
import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;

public class KmhServiceImpl implements KmhService {
	private CustomerRepository customerRepository;

	@Override
	public void closeKMH(DelegateExecution execution,CampaignApplicant applicant, String kmh) {
		Customer customer = getCustomerRepository().findCustomer(
				Long.parseLong(applicant.getCustomerid()));
		customer.setNumberOfKMH(customer.getNumberOfKMH() - 1);
		getCustomerRepository().updateCustomer(customer);
		applicant.setHasKMH(customer.getNumberOfKMH() > 0);
		execution.setVariable("applicant", applicant);
		if (!applicant.getHasKMH())
			System.out.println("apply kmhclose penalty for " + applicant + " "
					+ " kmh: " + kmh);
		System.out.println("kmh close called with " + applicant + " "
				+ " kmh: " + kmh);
	}

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
}

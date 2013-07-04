package com.globalmaksimum.bpmn.banking.delegates;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.globalmaksimum.bpmn.banking.domain.CampaignApplicant;
import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;

abstract public class AbstractBankingTaskDelegate implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		CampaignApplicant applicant = (CampaignApplicant) execution
				.getVariable("applicant");
		doExecute(execution, applicant);
	}

	private CustomerRepository customerRepository;

	abstract protected void doExecute(DelegateExecution execution,
			CampaignApplicant applicant);

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer getCustomer(CampaignApplicant applicant) {
		return getCustomerRepository().findCustomer(
				Long.parseLong(applicant.getCustomerid()));
	}
}
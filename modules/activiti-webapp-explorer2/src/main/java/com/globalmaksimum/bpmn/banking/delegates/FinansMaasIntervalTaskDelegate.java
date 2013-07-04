package com.globalmaksimum.bpmn.banking.delegates;


import java.util.Calendar;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

public class FinansMaasIntervalTaskDelegate extends
		AbstractBankingTaskDelegate {
	private Expression salaryInterval;

	@Override
	protected void doExecute(DelegateExecution execution,
			com.globalmaksimum.bpmn.banking.domain.CampaignApplicant applicant) {
		
			String value = (String) salaryInterval.getValue(execution);
			int days = Integer.parseInt(value);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		applicant.setMaasIntervalApproved(getCustomer(applicant)
				.getLastSalaryPayment().after(calendar.getTime()));

	}

	public Expression getSalaryInterval() {
		return salaryInterval;
	}

	public void setSalaryInterval(Expression salaryInterval) {
		this.salaryInterval = salaryInterval;
	}
}

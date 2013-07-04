package com.globalmaksimum.persistence.logging;

import java.util.Map.Entry;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class OutTest implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("here we are");
		for (Entry<String, Object> var : execution.getVariables().entrySet()) {
			System.out.println("var: " + var.getKey());
			System.out.println("val: " + var.getValue());
		}
	}

}

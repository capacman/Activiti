package com.globalmaksimum.bpmn.banking.domain;

import java.util.List;

public interface CustomerRepository {

	Customer findCustomer(Long id);

	Customer saveCustomer(Customer customer);

	Customer updateCustomer(Customer customer);

	List<Customer> listCustomers();
}

package com.globalmaksimum.bpmn.banking.domain.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerNotFound;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;

public class CustomerRepositoryImpl implements CustomerRepository {

	private EntityManager entityManager;

	@Override
	public Customer findCustomer(Long id) {
		Customer customer = entityManager.find(Customer.class, id);
		if (customer == null)
			throw new CustomerNotFound(id);
		return customer;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		this.entityManager.persist(customer);
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return this.entityManager.merge(customer);
	}

	@Override
	public List<Customer> listCustomers() {
		return this.entityManager.createQuery("select c from Customer c",
				Customer.class).getResultList();
	}

}

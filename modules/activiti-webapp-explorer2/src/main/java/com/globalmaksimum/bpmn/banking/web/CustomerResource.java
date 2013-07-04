package com.globalmaksimum.bpmn.banking.web;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.globalmaksimum.bpmn.banking.domain.Customer;
import com.globalmaksimum.bpmn.banking.domain.CustomerRepository;
import com.sun.jersey.api.json.JSONWithPadding;

@Path("/sim/customer")
@Produces({ MediaType.APPLICATION_JSON, "application/javascript",
		"application/x-javascript", "text/ecmascript",
		"application/ecmascript", "text/jscript" })
public class CustomerResource {

	private CustomerRepository customerRepository;

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GET
	public JSONWithPadding getCustomers() {
		return new JSONWithPadding(customerRepository.listCustomers());
	}

	@POST
	public JSONWithPadding createCustomer(Customer customer) {
		return new JSONWithPadding(customerRepository.saveCustomer(customer));
	}
}

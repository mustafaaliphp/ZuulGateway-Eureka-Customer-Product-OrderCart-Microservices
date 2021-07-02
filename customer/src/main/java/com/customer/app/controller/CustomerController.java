package com.customer.app.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.customer.app.exception.AuthenticationFailedException;
import com.customer.app.model.Customer;
import com.customer.app.service.CustomerService;
import com.customer.app.model.Cart;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")

public class CustomerController {

	private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());
	
	@Autowired
	CustomerService customerService;

	@PostMapping("/customers")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		LOG.log(Level.INFO, "/customer - > adding customer" );
		try {
			Customer _customer = customerService.addCustomer(customer);
			
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer/login")
	public @ResponseBody ResponseEntity<Customer> login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request)
			throws NoSuchAlgorithmException, AuthenticationFailedException {
		LOG.log(Level.INFO, "/customer - > login ");
		Customer customer = customerService.authentication(username, password);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		LOG.log(Level.INFO, "/customer - > getting list of customers" );
		try {
			List<Customer> Customers = new ArrayList<Customer>();
			Customers = customerService.getAllCustomers();
			if (Customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(Customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) throws Exception {
		LOG.log(Level.INFO, "/customer - > getting customer by Id");
		Optional<Customer> customerData = customerService.getCustomerById(id);

		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long customerId, @RequestBody Customer customer)
			throws Exception {
		LOG.log(Level.INFO, "/customer - > update customer by Id");
		Optional<Customer> customerData = customerService.getCustomerById(customerId);

		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setFirstName(customer.getFirstName());
			_customer.setLastName(customer.getLastName());
			_customer.setUsername(customer.getUsername());
			_customer.setPassword(customer.getPassword());
			return new ResponseEntity<>(customerService.updateCustomer(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long customerId) {
		LOG.log(Level.INFO, "/customer - > delete customer by Id");
		try {
			customerService.deleteCustomer(customerId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		LOG.log(Level.INFO, "/customer - > delete all customers");
		try {
			customerService.deleteAllCustomers();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// calling order service
	@PostMapping("/orders/{customerId}/carts")
	public @ResponseBody ResponseEntity<Void> createCart(@RequestBody Cart cart,
			@PathVariable(required = false) Long customerId) throws Exception {
		LOG.log(Level.INFO, "/order - > calling order Microservice and Creating cart");
		customerService.callOrderServiceAndCreateCart(cart, customerId);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	

	// Customer-Products Controller

//	@GetMapping("/products")
//	public String getListOfProducts() throws Exception {
//		LOG.log(Level.INFO, "/product - > Getting list of products from Product Microservice");
//		System.out.println("Getting list of products from Product Microservice");
//		return customerService.callProductServiceAndGetListOfProducts();
//	}
//
//	@GetMapping("/products/{productId}")
//	public String getProductById(@PathVariable("productId") Long productId) throws Exception {
//		LOG.log(Level.INFO, "/product - > Getting product by id from Product Microservice");
//		System.out.println("Getting product by id from Product Microservice");
//		return customerService.callProductServiceAndGetProductById(productId);
//	}
//
//	@GetMapping("/products/description/{description}")
//	public String getProductByDescription(@PathVariable("description") String description) throws Exception {
//		LOG.log(Level.INFO, "/product - > Getting products by description from Product Microservice");
//		System.out.println("Getting products by description from Product Microservice");
//		return customerService.callProductServiceAndGetProductByDescription(description);
//	}
//
//	@GetMapping("/products/categoryId/{id}")
//	public String getProductByCategoryId(@PathVariable("id") Long id) throws Exception {
//		LOG.log(Level.INFO, "/product - > Getting list of products from by category id Product Microservice");
//		System.out.println("Getting list of products from by category id Product Microservice");
//		return customerService.callProductServiceAndGetProductByCategoryId(id);
//	}
}
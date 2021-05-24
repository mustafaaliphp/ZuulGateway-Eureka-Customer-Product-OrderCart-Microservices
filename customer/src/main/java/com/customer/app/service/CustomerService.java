package com.customer.app.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.customer.app.model.Customer;
import com.customer.app.repository.CustomerRepository;
import com.customer.app.exception.AuthenticationFailedException;
import com.customer.app.util.ShaHashing;
import java.util.Date;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.customer.app.model.Cart;

@Service
@Transactional
public class CustomerService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	public Customer addCustomer(Customer customer) throws NoSuchAlgorithmException {
		customer.setPassword(ShaHashing.encrypted(customer.getPassword()));
		return customerRepository.save(customer);
	}

	public Customer authentication(String username, String password)
			throws NoSuchAlgorithmException, AuthenticationFailedException {
		Customer customer = customerRepository.findByUsername(username);
		if (customer.getPassword().equals(ShaHashing.encrypted(password)))
			return customer;
		else
			throw new AuthenticationFailedException();
	}

	public List<Customer> getAllCustomers() throws Exception {
		return customerRepository.findAll();
	}

	public Optional<Customer> getCustomerById(Long customerId) throws Exception {
		return customerRepository.findById(customerId);

	}

	public Customer updateCustomer(Customer body) throws Exception {

		return customerRepository.save(body);
	}

	public void deleteCustomer(Long customerId) throws Exception {
		customerRepository.deleteById(customerId);
	}

	public void deleteAllCustomers() throws Exception {
		customerRepository.deleteAll();
	}

	

	// Calling Order Service

	@HystrixCommand(fallbackMethod = "callOrderServiceAndCreateCart_Fallback")
	public String callOrderServiceAndCreateCart(Cart cart, Long customerId) throws Exception {
		ResponseEntity<String> response = restTemplateBuilder.build()
				.postForEntity("http://localhost:8083/api/orders/" + customerId + "/carts", cart, String.class);
		System.out.println("Response Received as " + response + " -  " + new Date());
		return response.toString();
	}

	@SuppressWarnings("unused")
	private String callOrderServiceAndCreateCart_Fallback(Cart cart, Long customerId) {
		System.out.println("Order Service is down!!! fallback route enabled...");
		return "CIRCUIT BREAKER ENABLED!!!No Response From Order Service at this moment. Service will be back shortly - "
				+ new Date();
	}

	// Calling Product service

//		@HystrixCommand(fallbackMethod = "callProductServiceAndGetListOfProducts_Fallback")
//		public String callProductServiceAndGetListOfProducts() throws Exception {
//			System.out.println("Getting list of products");
//			String response = restTemplate.exchange("http://product-service/api/products", HttpMethod.GET, null,
//					new ParameterizedTypeReference<String>() {
//					}).getBody();
	//
//			System.out.println("Response Received as " + response + " -  " + new Date());
	//
//			return "NORMAL FLOW !!! - List of Products -  ::: " + response + " -  " + new Date();
//		}
	//
//		@SuppressWarnings("unused")
//		private String callProductServiceAndGetListOfProducts_Fallback() {
//			System.out.println("Product Service is down!!! fallback route enabled...");
//			return "CIRCUIT BREAKER ENABLED!!!No Response From Product Service at this moment. Service will be back shortly - "
//					+ new Date();
//		}
	//
//		@HystrixCommand(fallbackMethod = "callProductServiceAndGetProductById_Fallback")
//		public String callProductServiceAndGetProductById(Long productId) throws Exception {
//			System.out.println("Getting product details for " + productId);
//			String response = restTemplate.exchange("http://product-service/api/products/{productId}", HttpMethod.GET, null,
//					new ParameterizedTypeReference<String>() {
//					}, productId).getBody();
	//
//			return "NORMAL FLOW !!! - productId -  " + productId + " :::  product Details " + response + " -  "
//					+ new Date();
//		}
	//
//		@SuppressWarnings("unused")
//		private String callProductServiceAndGetProductById_Fallback(Long productId) {
//			System.out.println("Product Service is down!!! fallback route enabled...");
//			return "CIRCUIT BREAKER ENABLED!!!No Response From Product Service at this moment. Service will be back shortly - "
//					+ new Date();
//		}
	//
//		@HystrixCommand(fallbackMethod = "callProductServiceAndGetProductByDescription_Fallback")
//		public String callProductServiceAndGetProductByDescription(String description) throws Exception {
//			System.out.println("Getting product details for " + description);
//			String response = restTemplate.exchange("http://product-service/api/products/description/{description}",
//					HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
//					}, description).getBody();
	//
//			System.out.println("Response Received as " + response + " -  " + new Date());
	//
//			return "NORMAL FLOW !!! - description -  " + description + " :::  product Details " + response + " -  "
//					+ new Date();
//		}
	//
//		@SuppressWarnings("unused")
//		private String callProductServiceAndGetProductByDescription_Fallback(String description) {
//			System.out.println("Product Service is down!!! fallback route enabled...");
//			return "CIRCUIT BREAKER ENABLED!!!No Response From Product Service at this moment. Service will be back shortly - "
//					+ new Date();
//		}
	//
//		@HystrixCommand(fallbackMethod = "callProductServiceAndGetProductByCategoryId_Fallback")
//		public String callProductServiceAndGetProductByCategoryId(Long id) throws Exception {
//			System.out.println("Getting product details for " + id);
//			String response = restTemplate.exchange("http://product-service/api/products/categoryId/{id}", HttpMethod.GET,
//					null, new ParameterizedTypeReference<String>() {
//					}, id).getBody();
	//
//			System.out.println("Response Received as " + response + " -  " + new Date());
	//
//			return "NORMAL FLOW !!! - id -  " + id + " :::  product Details " + response + " -  " + new Date();
//		}
	//
//		@SuppressWarnings("unused")
//		private String callProductServiceAndGetProductByCategoryId_Fallback(Long id) {
//			System.out.println("Product Service is down!!! fallback route enabled...");
//			return "CIRCUIT BREAKER ENABLED!!!No Response From Product Service at this moment. Service will be back shortly - "
//					+ new Date();
//		}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@LoadBalanced
	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}
}

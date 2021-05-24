package com.product.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.product.app.repositroy.CategoryRepository;
import com.product.app.repositroy.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.product.app.exception.ProductNotFoundException;
import com.product.app.model.Category;
import com.product.app.model.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	public Product save(Product product) {

		Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
		if (null == category) {
			category = new Category();
		}
		category.setDescription(product.getCategory().getDescription());
		product.setCategory(category);
		return productRepository.save(product);
	}

	public Product findById(Long idProduct) throws ProductNotFoundException {
		Product product = productRepository.findById(idProduct).orElse(null);
		if (product != null)
			return product;
		else
			throw new ProductNotFoundException();
	}

	public List<Product> findByDescription(String description) throws ProductNotFoundException {
		List<Product> product = productRepository.findByDescription(description);
		if (product != null)
			return product;
		else
			throw new ProductNotFoundException();
	}

	public List<Product> findByCategoryId(Long categoryId) throws ProductNotFoundException {
		List<Product> products = productRepository.findByCategoryId(categoryId);
		if (products.isEmpty() || products == null)
			throw new ProductNotFoundException();
		else
			return products;
	}

	public List<Product> findAll() throws ProductNotFoundException {
		List<Product> products = productRepository.findAll();
		if (products.isEmpty() || products == null)
			throw new ProductNotFoundException();
		else
			return products;
	}

	public Product updateProduct(Product product) throws Exception {
		return productRepository.save(product);
	}

	public void deleteProduct(Long productId) throws Exception {
		productRepository.deleteById(productId);
	}

	// Calling order service

	@HystrixCommand(fallbackMethod = "callOrderServiceAndAddProductToCart_Fallback")
	public String callOrderServiceAndAddProductToCart(Long cartId, Long ProductId, Integer quantity, BigDecimal price)
			throws Exception {
		ResponseEntity<String> response = restTemplateBuilder.build().postForEntity(
				"http://localhost:8083/api/orders/" + cartId + "/" + ProductId + "/" + quantity + "/" + price + "/",
				null, String.class);
		System.out.println("Response Received as " + response + " -  " + new Date());
		return response.toString();
	}

	@SuppressWarnings("unused")
	private String callOrderServiceAndAddProductToCart_Fallback(Long cartId, Long ProductId, Integer quantity,
			BigDecimal price) {
		System.out.println("Order Service is down!!! fallback route enabled...");
		return "CIRCUIT BREAKER ENABLED!!!No Response From Order Service at this moment. Service will be back shortly - "
				+ new Date();
	}

	@LoadBalanced
	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

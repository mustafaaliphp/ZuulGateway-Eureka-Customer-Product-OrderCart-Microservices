package com.product.app.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.product.app.service.ProductService;
import com.product.app.exception.ProductNotFoundException;
import com.product.app.model.Product;
import com.product.app.repositroy.CategoryRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
	
	@Autowired
	ProductService productService;

	@Autowired
	CategoryRepository categoryRepository;

	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception {
		LOG.log(Level.INFO, "/product - > adding product" );
		try {
			Product _product = productService.save(product);

			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products")
	public @ResponseBody ResponseEntity<List<Product>> getProducts() throws ProductNotFoundException {
		LOG.log(Level.INFO, "/product - > getting list of products" );
		List<Product> products = productService.findAll();
		return new ResponseEntity<List<Product>>(products, HttpStatus.FOUND);
	}

	@GetMapping("/products/{productId}")
	public @ResponseBody ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId)
			throws ProductNotFoundException {
		LOG.log(Level.INFO, "/product - > getting products by Id" );
		Product product = productService.findById(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/products/description/{description}")
	public @ResponseBody ResponseEntity<List<Product>> getProductByDescription(
			@PathVariable("description") String description) throws ProductNotFoundException {
		LOG.log(Level.INFO, "/product - > getting list of products by description" );
		List<Product> products = productService.findByDescription(description);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/products/categoryId/{id}")
	public @ResponseBody ResponseEntity<List<Product>> findByCategoryId(@PathVariable("id") Long id)
			throws ProductNotFoundException {
		LOG.log(Level.INFO, "/product - > getting category by Id" );
		List<Product> products = productService.findByCategoryId(id);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product)
			throws Exception {
		LOG.log(Level.INFO, "/product - > updating product" );
		try {
			Product _product = productService.save(product);

			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long productId) {
		LOG.log(Level.INFO, "/product - > deleting product" );
		try {
			productService.deleteProduct((long) productId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// calling order service

	@PostMapping("/products/{cartId}/{ProductId}/{quantity}/{price}")
	public @ResponseBody ResponseEntity<Void> addProductToItemList(@PathVariable("cartId") Long cartId,
			@PathVariable("ProductId") Long ProductId, @PathVariable("quantity") Integer quantity,
			@PathVariable("price") BigDecimal price) throws Exception {
		LOG.log(Level.INFO, "/product - > calling order service and adding item" );
		productService.callOrderServiceAndAddProductToCart(cartId, ProductId, quantity, price);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}

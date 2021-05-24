package com.order.app.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.order.app.model.Cart;
import com.order.app.model.Order;
import com.order.app.service.OrderService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")
public class OrderController {

	private static final Logger LOG = Logger.getLogger(OrderController.class.getName());
	
	@Autowired
	OrderService orderService;

	@PostMapping("/orders/{customerId}/carts")
	public @ResponseBody ResponseEntity<Void> createCart(@RequestBody Cart cart,
			@PathVariable(required = false) Long customerId, HttpServletRequest request) throws Exception {
		LOG.log(Level.INFO, "/order - > Creating customer cart" );
		if (customerId != null && cart.getCustomerId() == null) {
			cart.setCustomer(customerId);
		}
		Cart savedCart = orderService.save(cart);
		HttpHeaders header = new HttpHeaders();
		header.setLocation(new URI(request.getRequestURL() + "/" + savedCart.getCartId().toString()));
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}

	@PostMapping("/orders/{cartId}/{ProductId}/{quantity}/{price}")
	public @ResponseBody ResponseEntity<Void> addProductToLineItem(@PathVariable("cartId") Long cartId,
			@PathVariable("ProductId") Long ProductId, @PathVariable("quantity") Integer quantity,
			@PathVariable("price") BigDecimal price) throws Exception {
		LOG.log(Level.INFO, "/order - > adding Product To LineItem");
		orderService.add(cartId, ProductId, quantity, price);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping("/orders/{cartId}")
	public @ResponseBody ResponseEntity<Void> OrderCheckout(@PathVariable("cartId") Long cartId,
			HttpServletRequest request) throws Exception {
		LOG.log(Level.INFO, "/order - > Order Checkout");
		Order order = orderService.OrderCheckout(cartId);
		HttpHeaders header = new HttpHeaders();
		header.setLocation(new URI(request.getRequestURL() + "/" + order.getId().toString()));
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/orders/cart/{cartId}")
	public ResponseEntity<HttpStatus> deleteCart(@PathVariable("cartId") Long cartId,
			HttpServletRequest request) throws Exception {
		LOG.log(Level.INFO, "/product - > deleting cart" );
		try {
			orderService.deleteCart(cartId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/orders/lineItem/{cartId}")
	public ResponseEntity<HttpStatus> deleteAllListOfIteams(@PathVariable("cartId") Long cartId,
			HttpServletRequest request) throws Exception {
		LOG.log(Level.INFO, "/product - > deleting cart" );
		try {
			orderService.deletelineItem(cartId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
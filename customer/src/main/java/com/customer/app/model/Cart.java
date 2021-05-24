package com.customer.app.model;

public class Cart  {

	
	private Long cartId;
	private Long customerId;
	

	public Cart() {
	}

	public Cart(Long cartId, Long customerId) {
		this.cartId = cartId;
		this.customerId = customerId;
		
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setCustomer(Long customerId) {
		this.customerId = customerId;
	}

}


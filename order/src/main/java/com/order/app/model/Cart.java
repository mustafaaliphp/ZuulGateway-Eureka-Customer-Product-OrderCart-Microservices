package com.order.app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "carts", catalog = "order_db")


public class Cart  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartId_seq")
	@SequenceGenerator(initialValue = 1, name = "cartId_seq", sequenceName = "cartId_sequence")
	@Column(name = "cartId")
	private Long cartId;
	private Long customerId;
	//private BigDecimal subtotal;
	

	@OneToMany(fetch = FetchType.EAGER , mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<LineItem> linesItems = new ArrayList<LineItem>();
    
	public Cart() {
	}

	public Cart(Long cartId, Long customerId) {
		this.cartId = cartId;
		this.customerId = customerId;
		//this.subtotal = subtotal;
	}

	public Cart(Long cartId, Long customerId, List<LineItem> linesItems) {
		this.cartId = cartId;
		this.customerId = customerId;
		//this.subtotal = subtotal;
		this.linesItems = linesItems;
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

//	@Column(name = "subtotal", nullable = false, precision = 10)
//	public BigDecimal getSubtotal() {
//		return this.subtotal;
//	}
//
//	public void setSubtotal(BigDecimal subtotal) {
//		this.subtotal = subtotal;
//	}

	public List<LineItem> getLinesItems() {
		return this.linesItems;
	}

	public void setLinesItems(List<LineItem> linesItems) {
		this.linesItems = linesItems;
	}
	
	public double  calculateTotal(){
		double  total = 0;
		for (LineItem lineItem : this.getLinesItems()) {
			total+= lineItem.getPrice().doubleValue() * lineItem.getQuantity();
			
		}
		return total;
	}
}

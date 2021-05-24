package com.order.app.model;



import java.math.BigDecimal;

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
@Table(name = "lines_item", catalog = "order_db")
public class LineItem  {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lineItem_seq")
	@SequenceGenerator(initialValue = 1, name = "lineItem_seq", sequenceName = "lineItem_sequence")
	@Column(name = "linesItemId")
	private Long linesItemId;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(foreignKey = @ForeignKey(name = "cartId"), name = "cartId")
	private Cart cart;
	private Long productId;
	

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(foreignKey = @ForeignKey(name = "orderId"), name = "orderId")
	private Order order;
	private Integer quantity;
	private BigDecimal price;
	
	public LineItem() {
	}

	public LineItem(Cart cart, Long productId, Order order, Integer quantity, BigDecimal price) {
		this.cart = cart;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
	}

	
	public Long getIdlinesItem() {
		return this.linesItemId;
	}

	public void setIdlinesItem(Long linesItemId) {
		this.linesItemId = linesItemId;
	}

	

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Long getProduct() {
		return this.productId;
	}

	public void setProduct(Long productId) {
		this.productId = productId;
	}

	
	
	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Column(name = "quantity", nullable = false)
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "price", nullable = false, precision = 10)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}

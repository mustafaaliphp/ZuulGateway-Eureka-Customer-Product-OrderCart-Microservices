package com.order.app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OrderHistory", catalog = "order_db")
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderHistory_seq")
	@SequenceGenerator(initialValue = 1, name = "orderHistory_seq", sequenceName = "orderHistory_sequence")
	@Column(name = "orderHistoryId")
	private Long orderHistoryId;
	private Long linesItemId;
	private Long customerId;
	private Long productId;
	private Long cartId;
	private BigDecimal price;
	private Integer quantity;
	private Date ordered;
	private Long orderId;
	public OrderHistory() {
		super();
	}
	public OrderHistory(Long orderHistoryId, Long linesItemId, Long customerId, Long productId, Long cartId,
			BigDecimal price, Integer quantity, Date ordered, Long orderId) {
		super();
		this.orderHistoryId = orderHistoryId;
		this.linesItemId = linesItemId;
		this.customerId = customerId;
		this.productId = productId;
		this.cartId = cartId;
		this.price = price;
		this.quantity = quantity;
		this.ordered = ordered;
		this.orderId = orderId;
	}
	public Long getOrderHistoryId() {
		return orderHistoryId;
	}
	public void setOrderHistoryId(Long orderHistoryId) {
		this.orderHistoryId = orderHistoryId;
	}
	public Long getLinesItemId() {
		return linesItemId;
	}
	public void setLinesItemId(Long linesItemId) {
		this.linesItemId = linesItemId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getOrdered() {
		return ordered;
	}
	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
}

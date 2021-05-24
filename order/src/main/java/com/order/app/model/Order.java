package com.order.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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


import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Orders", catalog = "order_db")
public class Order {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
	@SequenceGenerator(initialValue = 1, name = "order_seq", sequenceName = "order_sequence")
	@Column(name = "orderId")
	private Long orderId;
	private Long customerId;
	private Date ordered;
	private String status;
	private double total;
	
	@OneToMany(fetch = FetchType.EAGER , mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<LineItem> linesItems = new ArrayList<LineItem>();

	public Order() {
	}

	
	public Order(Long orderId, Long customerId, Date ordered, String status, double total, List<LineItem> linesItems) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.ordered = ordered;
		this.status = status;
		this.total = total;
		this.linesItems = linesItems;
	}


	public Long getId() {
		return orderId;
	}


	public void setId(Long id) {
		this.orderId = orderId;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ordered", nullable = false, length = 19)
	public Date getOrdered() {
		return this.ordered;
	}

	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "total", nullable = false, precision = 10)
	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	
	
	public List<LineItem> getLinesItems() {
		return this.linesItems;
	}

	public void setLinesItems(List<LineItem> linesItems) {
		this.linesItems = linesItems;
	}

	public static class BuilderOrder {

		private Long orderId;
		private Long customerId;
		private Date ordered;
		private String status;
		private double total;
		private List<LineItem> linesItems = new ArrayList<LineItem>();

		public BuilderOrder setIdorder(Long orderId) {
			this.orderId = orderId;
			return this;
		}

		public BuilderOrder setCustomer(Long customerId) {
			this.customerId = customerId;
			return this;
		}

		public BuilderOrder setOrdered(Date ordered) {
			this.ordered = ordered;
			return this;
		}

		public BuilderOrder setStatus(String status) {
			this.status = status;
			return this;
		}

		public BuilderOrder setTotal(double d) {
			this.total = d;
			return this;
		}

		public BuilderOrder setLinesItems(List<LineItem> linesItems) {
			this.linesItems = linesItems;
			return this;
		}

		public Order build() {
			Order order = new Order(this.orderId, this.customerId, this.ordered, 
					this.status, this.total, this.linesItems);
			return order;
		}
	}
}

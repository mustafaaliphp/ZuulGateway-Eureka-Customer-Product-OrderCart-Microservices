package com.product.app.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product", catalog = "product_db")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	@SequenceGenerator(initialValue = 1, name = "product_seq", sequenceName = "product_sequence")
	@Column(name = "idProduct")
	private Long idProduct;

	@Column(name = "description")
	private String description;
	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(foreignKey = @ForeignKey(name = "category_id"), name = "category_id")
	private Category category;

	public Product() {
		super();
	}

	public Product(String description, BigDecimal price, Category category) {
		super();
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Product(Long idProduct, String description, BigDecimal price, Category category) {
		super();
		this.idProduct = idProduct;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
}

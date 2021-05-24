package com.product.app.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByDescription(String description);

	List<Product> findByCategoryId(Long categoryId);

}

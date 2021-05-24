package com.product.app.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}

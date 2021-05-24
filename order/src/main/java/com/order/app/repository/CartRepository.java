package com.order.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.app.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

//	void update(Cart cart);
//	Cart findByIdCart(Long cartId);

}

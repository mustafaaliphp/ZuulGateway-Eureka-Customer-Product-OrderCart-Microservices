package com.order.app.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.order.app.model.Cart;
import com.order.app.model.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Long>{
	
	List<LineItem> findByCart(Cart cart);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE LineItem SET order_id = :orderId WHERE cart_id = :cartId")
	void update(@Param("cartId") Long cartId, Long orderId);
	
}

package com.order.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.order.app.model.Cart;
import com.order.app.model.LineItem;
import com.order.app.model.Order;
import com.order.app.model.Order.BuilderOrder;
import com.order.app.repository.CartRepository;
import com.order.app.repository.LineItemRepository;
import com.order.app.repository.OrderRepository;
import com.order.app.util.OrderStatus;

@Service
@Transactional
public class OrderService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	LineItemRepository lineItemRepository;

	public Cart save(Cart cart) throws Exception{
		return cartRepository.save(cart);
	}

	public Cart add(Long cartId, Long productId, Integer quantity, BigDecimal price) throws Exception{
		Cart cart = cartRepository.findById(cartId).orElse(null);

		cart.getLinesItems().add(new LineItem(cart, productId, null, quantity, price));
		return cartRepository.save(cart);

	}

	public Order OrderCheckout(Long cartId) throws Exception {

		Cart cart = cartRepository.findById(cartId).orElse(null);
		Order order = new BuilderOrder().setCustomer(cart.getCustomerId()).setOrdered(new Date())
				.setStatus(OrderStatus.NEW.toString()).setTotal(cart.calculateTotal()).build();
		Order savedOrder = orderRepository.save(order);
		
		// Insert order Id to line item.
		lineItemRepository.update(cartId, savedOrder.getId());
		
		// Delete all items form the cart and make empty to the next order after checkout
		deletelineItem(cartId);
		return savedOrder;

	}

	public void deleteCart(Long cartId) throws Exception {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		if(cart != null) {
			cartRepository.delete(cart);
		}
	}
	
	public void deletelineItem(Long cartId) throws Exception {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		if(cart != null) {
			List <LineItem> lineItems = lineItemRepository.findByCart(cart);
			if(lineItems != null) {
				lineItemRepository.deleteAll(lineItems);
			}
		}
		
	}
}
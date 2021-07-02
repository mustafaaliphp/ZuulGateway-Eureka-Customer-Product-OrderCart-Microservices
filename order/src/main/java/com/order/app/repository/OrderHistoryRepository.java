package com.order.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.app.model.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

	List<OrderHistory> findAllByCustomerId(Long customerId);

}

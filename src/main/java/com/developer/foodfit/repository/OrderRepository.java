package com.developer.foodfit.repository;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserIdOrderByRegTimeDesc(Long userId, PageRequest id);
    Long countByOrderStatusAndUserIdAndOrderViewStatus(OrderStatus status, Long userId, String viewStatus);
}

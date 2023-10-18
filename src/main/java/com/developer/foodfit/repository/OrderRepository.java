package com.developer.foodfit.repository;

import com.developer.foodfit.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.developer.foodfit.repository;

import com.developer.foodfit.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByUserId(Long id);
}

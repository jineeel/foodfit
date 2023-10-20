package com.developer.foodfit.repository;

import com.developer.foodfit.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
    List<CartItem> findByCartId(Long cartId);
}

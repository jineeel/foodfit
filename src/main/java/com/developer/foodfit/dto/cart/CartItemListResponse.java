package com.developer.foodfit.dto.cart;

import com.developer.foodfit.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartItemListResponse {
    private Long id;
    private Long cartId;
    private Long itemId;
    private int count;

    public CartItemListResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cartId = cartItem.getCart().getId();
        this.itemId = cartItem.getItem().getId();
        this.count = cartItem.getCount();
    }
}

package com.developer.foodfit.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CartViewResponse {
    private Long cartId;
    private int cartItemCount;

    public void update(Long cartId, int cartItemCount) {
        this.cartId = cartId;
        this.cartItemCount = cartItemCount;
    }
}

package com.developer.foodfit.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponse {
    private Long itemId;
    private Long itemCount;

    public OrderItemResponse(Long itemId, Long itemCount) {
        this.itemId = itemId;
        this.itemCount = itemCount;
    }
}

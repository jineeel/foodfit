package com.developer.foodfit.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class OrderViewListResponse {
    private Long orderId;
    private Long orderItemId;
    private Long itemId;
    private String orderItemName;
    private int totalOrderPrice;
    private int totalOrderCount;
    private LocalDateTime regTime;


    public OrderViewListResponse(Long orderId, Long orderItemId, Long itemId, String orderItemName) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.orderItemName = orderItemName;

    }
    public void updateTotalPrice(int totalOrderPrice){
        this.totalOrderPrice += totalOrderPrice;
    }
}
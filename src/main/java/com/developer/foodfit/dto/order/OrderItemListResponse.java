package com.developer.foodfit.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemListResponse {
    private String orderItemName;
    private int orderPrice;
    private int orderCount;


    public OrderItemListResponse(String orderItemName, int orderPrice, int orderCount) {
        this.orderItemName = orderItemName;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
}

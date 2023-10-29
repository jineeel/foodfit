package com.developer.foodfit.dto.order;

import com.developer.foodfit.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemListResponse {
    private Long orderId;
    private Long orderItemId;
    private Long itemId;
    private String orderItemName;
    private int orderPrice;
    private int orderCount;
    private LocalDateTime regTime;

    public OrderItemListResponse(String orderItemName, int orderPrice, int orderCount) {
        this.orderItemName = orderItemName;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
    public OrderItemListResponse(OrderItem orderItem) {
        this.orderId = orderItem.getOrder().getId();
        this.orderItemId = orderItem.getId();
        this.itemId = orderItem.getItem().getId();
        this.orderItemName = orderItem.getItem().getItemName();
        this.orderPrice = orderItem.getOrderPrice();
        this.orderCount = orderItem.getCount();
    }
}

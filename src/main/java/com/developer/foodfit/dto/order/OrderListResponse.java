package com.developer.foodfit.dto.order;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderListResponse {
    private Long orderId;
    private Long userId;
    private Long orderItemId;
    private OrderStatus orderStatus;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String orderViewStatus;

    public OrderListResponse(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.orderStatus = order.getOrderStatus();
        this.regTime = order.getRegTime();
        this.orderViewStatus = order.getOrderViewStatus();
    }
}

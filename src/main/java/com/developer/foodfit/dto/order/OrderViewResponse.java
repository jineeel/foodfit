package com.developer.foodfit.dto.order;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderViewResponse {
    private Long orderId;
    private Long userId;
    private Long itemId;
    private String orderName;
    private String orderPhone;
    private String orderZipcode;
    private String orderStreetAdr;
    private String orderDetailAdr;
    private String orderMessage;
    private OrderStatus orderStatus;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;


    public OrderViewResponse(Order order) {
        this.userId = order.getUser().getId();
        this.orderName = order.getOrderName();
        this.orderPhone = order.getOrderPhone();
        this.orderZipcode = order.getOrderZipcode();
        this.orderStreetAdr = order.getOrderStreetAdr();
        this.orderDetailAdr = order.getOrderDetailAdr();
        this.orderMessage = order.getOrderMessage();
        this.orderStatus = order.getOrderStatus();
        this.regTime = order.getRegTime();
    }
}

package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Order;
import com.developer.foodfit.domain.OrderItem;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.dto.order.AddOrderRequest;
import com.developer.foodfit.dto.order.AddOrderUserRequest;
import com.developer.foodfit.service.OrderItemService;
import com.developer.foodfit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderApiController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    /** 주문 등록 **/
    @PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(@RequestBody AddOrderRequest addOrderRequest, Principal principal) {
        Order saveOrder = orderService.save(addOrderRequest.getOrderItemRequests(), addOrderRequest.getOrderUserRequest(), principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrder);
    }
}


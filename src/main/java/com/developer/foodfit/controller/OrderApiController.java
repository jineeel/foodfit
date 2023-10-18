package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Order;
import com.developer.foodfit.domain.OrderItem;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.service.OrderItemService;
import com.developer.foodfit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class OrderApiController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    /** 주문 등록 **/
    @PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(AddOrderItemRequest request, Principal principal){
        Order saveOrder = orderService.save(request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrder);
    }
}

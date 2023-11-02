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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    /** 주문 등록 **/
    @PostMapping("/api/order")
    public ResponseEntity<Order> createOrder(@RequestBody AddOrderRequest addOrderRequest, Principal principal) {
        Order saveOrder = orderService.save(addOrderRequest.getOrderItemRequests(), addOrderRequest.getOrderUserRequest(), principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrder);
    }

    /** 주문 삭제 **/
    @PostMapping("/api/order/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable(value = "id") Long orderId){
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }

    /** 주문 취소 **/
    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable(value = "id") Long orderId){
        orderService.cancel(orderId);
        return ResponseEntity.ok().build();
    }

    /** 주문 상태 변경 **/
    @PutMapping("/order/updateStatus/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "orderId") String orderId, @RequestBody String status){
        orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok().build();
    }
}


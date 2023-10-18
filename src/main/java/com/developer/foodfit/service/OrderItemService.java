package com.developer.foodfit.service;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.Order;
import com.developer.foodfit.domain.OrderItem;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem save(AddOrderItemRequest request, Order order, Item item) {
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderPrice(request.getOrderPrice())
                .count(request.getCount())
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        orderItemRepository.save(orderItem);
        return orderItem;
    }
}
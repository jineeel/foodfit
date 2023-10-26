package com.developer.foodfit.service;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.Order;
import com.developer.foodfit.domain.OrderItem;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.dto.order.AddOrderUserRequest;
import com.developer.foodfit.repository.ItemRepository;
import com.developer.foodfit.repository.OrderItemRepository;
import com.developer.foodfit.repository.OrderRepository;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public Order save(List<AddOrderItemRequest> orderItemRequest, AddOrderUserRequest orderUserRequest, Principal principal) {
        User user = userRepository.findByUserId(principal.getName()).orElseThrow();
        Order order = Order.builder()
                .user(user)
                .orderName(orderUserRequest.getOrderName())
                .orderPhone(orderUserRequest.getOrderPhone())
                .orderZipcode(orderUserRequest.getOrderZipcode())
                .orderStreetAdr(orderUserRequest.getOrderStreetAdr())
                .orderDetailAdr(orderUserRequest.getOrderDetailAdr())
                .orderMessage(orderUserRequest.getOrderMessage())
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        for(int i=0; i<orderItemRequest.size(); i++){
            Item Item = itemRepository.findById(orderItemRequest.get(i).getItemId()).orElseThrow();
            orderItemService.save(orderItemRequest.get(i),order,Item);
        }

        return order;
    }


    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}

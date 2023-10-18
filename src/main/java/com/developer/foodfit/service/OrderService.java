package com.developer.foodfit.service;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.Order;
import com.developer.foodfit.domain.OrderItem;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.repository.ItemRepository;
import com.developer.foodfit.repository.OrderRepository;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemRepository itemRepository;

    public Order save(AddOrderItemRequest request, Principal principal) {
        User user = userRepository.findByUserId(principal.getName()).orElseThrow();
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        orderRepository.save(order);
//        System.out.println("request.getItemId()="+request.getItemId());
        Item Item = itemRepository.findById(request.getItemId()).orElseThrow();

//        for(int i=0; i<orderItemList.size(); i++){
//            OrderItem orderItem = new OrderItem();
//            orderItem.
//        }
        orderItemService.save(request,order,Item);
        return order;
    }


}

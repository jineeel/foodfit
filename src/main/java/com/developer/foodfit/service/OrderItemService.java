package com.developer.foodfit.service;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.*;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;
    private final CartItemService cartItemService;
    private final CartService cartService;

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

        //상품 수량 수정
        itemService.updateStockNumber(orderItem.getItem().getId(),request.getCount());

        //장바구니 상품 삭제
        Item itemId = itemService.findById(item.getId());
        Cart Cart = cartService.findCartId(order.getUser().getId());
        CartItem cartItemId = cartItemService.findCartItemId(Cart.getId(), itemId.getId());
        cartItemService.delete(cartItemId.getId());

        return orderItem;
    }
}
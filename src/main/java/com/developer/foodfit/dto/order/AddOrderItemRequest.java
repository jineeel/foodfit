package com.developer.foodfit.dto.order;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.Order;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddOrderItemRequest {
    private Long id;
    private Item item;
    private Long itemId;
    private Order order;
    private int orderPrice;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}
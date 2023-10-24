package com.developer.foodfit.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOrderRequest {
    private List<AddOrderItemRequest> orderItemRequests;
    private AddOrderUserRequest orderUserRequest;
}

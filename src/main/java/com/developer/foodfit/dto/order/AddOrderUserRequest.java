package com.developer.foodfit.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrderUserRequest {
    private String orderName;
    private String orderZipcode;
    private String orderStreetAdr;
    private String orderDetailAdr;
    private String orderPhone;
    private String orderMessage;
}

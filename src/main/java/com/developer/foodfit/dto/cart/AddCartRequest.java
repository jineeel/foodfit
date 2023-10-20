package com.developer.foodfit.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddCartRequest {

//    private Long id;
    private Long itemId;
    private int count;
}

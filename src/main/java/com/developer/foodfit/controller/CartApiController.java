package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.Order;
import com.developer.foodfit.dto.cart.AddCartRequest;
import com.developer.foodfit.dto.order.AddOrderItemRequest;
import com.developer.foodfit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/api/cart")
    public ResponseEntity<Cart> addCart(@RequestBody AddCartRequest request, Principal principal){
        Cart addCart = cartService.save(request,principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCart);
    }
}

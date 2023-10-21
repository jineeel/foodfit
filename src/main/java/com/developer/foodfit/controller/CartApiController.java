package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.CartItem;
import com.developer.foodfit.dto.cart.AddCartRequest;
import com.developer.foodfit.service.CartItemService;
import com.developer.foodfit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CartApiController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    /** 장바구니 추가 **/
    @PostMapping("/api/cart")
    public ResponseEntity<Cart> addCart(@RequestBody AddCartRequest request, Principal principal){
        Cart addCart = cartService.save(request,principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCart);
    }

    /** 장바구니 수량 수정 **/
    @PutMapping("/api/cart/{cartItemId}")
    public ResponseEntity<CartItem> updateCount(@PathVariable("cartItemId") Long cartItemId, @RequestParam("count") int count){
        CartItem cartItem = cartItemService.updateCount(cartItemId, count);
        return ResponseEntity.ok().body(cartItem);
    }

    /** 장바구니 상품 삭제 **/
    @DeleteMapping("/api/cart/{cartItemId}")
    public ResponseEntity<CartItem> deleteCart(@PathVariable("cartItemId") Long cartItemId){
        cartItemService.delete(cartItemId);
        return ResponseEntity.ok().build();
    }
}

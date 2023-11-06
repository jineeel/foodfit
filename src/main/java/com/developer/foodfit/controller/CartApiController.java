package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.CartItem;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.cart.AddCartRequest;
import com.developer.foodfit.dto.cart.CartViewResponse;
import com.developer.foodfit.service.CartItemService;
import com.developer.foodfit.service.CartService;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartApiController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    /* 장바구니 추가 */
    @PostMapping("/api/cart")
    public ResponseEntity<Cart> addCart(@RequestBody AddCartRequest request, Principal principal){
        Cart addCart = cartService.save(request,principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCart);
    }

    /* 장바구니 상품 수정 */
    @PutMapping("/api/cart/{cartItemId}")
    public ResponseEntity<CartItem> updateCount(@PathVariable("cartItemId") Long cartItemId, @RequestBody int count){
        CartItem updateCartItem = cartItemService.updateCount(cartItemId, count);
        return ResponseEntity.ok().body(updateCartItem);
    }

    /* 장바구니 상품 삭제 */
    @DeleteMapping("/api/cart/{cartItemId}")
    public ResponseEntity<CartItem> deleteCart(@PathVariable("cartItemId") String cartItemId, Principal principal){
        String[] itemIds = cartItemId.split(",");
        Cart cart = cartService.findCart(principal);
        for (String itemId : itemIds) {
            Long resultItemId= Long.parseLong(itemId);
            CartItem cartItemIds = cartItemService.findCartItemId(cart.getId(), resultItemId);
            cartItemService.delete(cartItemIds.getId());
        }
        return ResponseEntity.ok().build();
    }

    /* 장바구니 아이템 수량 */
    @PostMapping("/api/cart/itemCount")
    public ResponseEntity<CartViewResponse> findCartCount(Principal principal){
        if(principal==null){
            return ResponseEntity.ok().body(null);
        }
        User user = userService.findByUserId(principal.getName());
        CartViewResponse cartViewResponse = new CartViewResponse();
        if(user!=null){
            List<CartItem> cartItems = cartItemService.findCart(user);
            for (CartItem cartItem : cartItems) {
                cartViewResponse.update(cartItem.getCart().getId(), cartItems.size());
            }
        }
        return ResponseEntity.ok().body(cartViewResponse);
    }
}
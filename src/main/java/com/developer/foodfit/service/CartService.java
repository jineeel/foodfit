package com.developer.foodfit.service;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.cart.AddCartRequest;
import com.developer.foodfit.repository.CartItemRepository;
import com.developer.foodfit.repository.CartRepository;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;


@RequiredArgsConstructor
@Service
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    /** 장바구니 저장 **/
    public Cart save(AddCartRequest request, Principal principal) {
        User user = userRepository.findByUserId(principal.getName()).orElseThrow();

        Cart cart = Cart.builder()
                .user(user)
                .build();

        Cart targetCart;
        Cart userCart = cartRepository.findCartByUserId(user.getId());

        if(userCart==null){
            cartRepository.save(cart);
            targetCart=cart;
        }else{
           targetCart=userCart;
        }
        cartItemService.save(request,targetCart);

        return cart;
    }


    public Cart findCart(Principal principal) {
        User user = userRepository.findByUserId(principal.getName()).orElseThrow();
        Cart cart = cartRepository.findCartByUserId(user.getId());
        return cart;
    }

    public Cart findCartId(Long userId){
        return cartRepository.findCartByUserId(userId);
    }
}

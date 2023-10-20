package com.developer.foodfit.service;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.CartItem;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.cart.AddCartRequest;
import com.developer.foodfit.repository.CartItemRepository;
import com.developer.foodfit.repository.CartRepository;
import com.developer.foodfit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public CartItem save(AddCartRequest request,Cart cart) {
        Item item = itemRepository.findById(request.getItemId()).orElseThrow();

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .count(request.getCount())
                .build();

        Cart targetCart = cartRepository.findCartByUserId(cart.getUser().getId());
        CartItem targetCartItem = cartItemRepository.findByCartIdAndItemId(targetCart.getId(), item.getId());

        if(targetCartItem==null){
            cartItemRepository.save(cartItem);
        }else{
            // 장바구니에 이미 있는 아이템이면 수량만 더하기
            targetCartItem.updateCount(request.getCount());
            return targetCartItem;
        }
        return cartItem;
    }

    public List<CartItem> findCartItem(Cart cart) {
        return cartItemRepository.findByCartId(cart.getId());
    }
}
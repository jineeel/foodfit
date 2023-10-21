package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Cart;
import com.developer.foodfit.domain.CartItem;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.dto.cart.CartItemListResponse;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.service.CartItemService;
import com.developer.foodfit.service.CartService;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ItemImgService itemImgService;
    private final ItemService itemService;

    @GetMapping("/cart")
    public String findCart(Principal principal, Model model){
        Cart cart = cartService.findCart(principal);
        if(cart!=null){
            List<CartItemListResponse> cartItemList = cartItemService.findCartItem(cart).stream().map(CartItemListResponse::new).toList();

            List<ItemListResponse> itemList = cartItemList.stream()
                    .map(cartItem -> itemService.findItem(cartItem.getItemId()))
                    .flatMap(List::stream) // Flattens the list of items
                    .map(ItemListResponse::new)
                    .collect(Collectors.toList());

            List<ItemImgResponse> regImgList = itemList.stream()
                    .map(item -> itemImgService.findRegImgListItemId(item.getItemId()))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("itemList", itemList);
            model.addAttribute("regImgList", regImgList);
        }

        return "cart/cartForm";
    }

}

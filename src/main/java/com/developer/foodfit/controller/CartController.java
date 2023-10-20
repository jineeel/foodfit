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
        List<CartItemListResponse> cartItemList = cartItemService.findCartItem(cart).stream().map(CartItemListResponse::new).toList();

        List<ItemListResponse> itemList = cartItemList.stream()
                .map(cartItem -> itemService.findItem(cartItem.getItemId()))
                .flatMap(List::stream) // Flattens the list of items
                .map(ItemListResponse::new)
                .collect(Collectors.toList());

        List<ItemImgResponse> itemImgList = itemList.stream()
                        .map(item -> itemImgService.findItemImg(item.getItemId()))
                        .flatMap(List::stream)
                        .map(ItemImgResponse::new)
                        .collect(Collectors.toList());

        List<List<String>> regImgLists = itemImgList.stream()
                .map(itemImg -> itemImgService.findRegImgListItemId(itemImg.getItemId()))
                .collect(Collectors.toList());

        for (int i=0; i<regImgLists.size(); i++){
            System.out.println(regImgLists.get(i).toString());
        }
//        List<CategoryResponse> categoryResponsesList = categoryService.findCategoryList(categoryCode).stream().map(CategoryResponse::new).toList();
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("regImgLis", regImgLists);
        return "cart/cartForm";
    }

}

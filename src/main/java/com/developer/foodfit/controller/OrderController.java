package com.developer.foodfit.controller;

import com.developer.foodfit.domain.*;
import com.developer.foodfit.dto.cart.CartItemListResponse;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.dto.item.ItemViewResponse;
import com.developer.foodfit.dto.order.OrderViewResponse;
import com.developer.foodfit.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final UserService userService;
    private final CartItemService cartItemService;

    /** 주문 **/
    @GetMapping("/order")
    public String order(@RequestParam(value = "id")String cartItemId, @RequestParam(value="quantity") String quantity, Model model, Principal principal){
        String[] itemIds = cartItemId.split(","); // 상품 ID
        String[] quantities = quantity.split(",");

        List<OrderViewResponse> orderViewList = new ArrayList<>();
        List<ItemListResponse> itemList = new ArrayList<>();
        List<ItemImgResponse> itemRepImgList = new ArrayList<>();

        for(int i=0; i<itemIds.length; i++){
            Long itemId = Long.parseLong(itemIds[i]);
            Long itemCount = Long.parseLong(quantities[i]);

            Item item = itemService.findById(itemId);

            orderViewList.add(new OrderViewResponse(itemId,itemCount));
            itemList.add(new ItemListResponse(item));
            ItemImgResponse itemImgResponse = new ItemImgResponse(itemImgService.findRegImgItemIds(item.getId()));
            itemRepImgList.add(itemImgResponse);
        }
        model.addAttribute("user",userService.findByUserId(principal.getName()));
        model.addAttribute("item", itemList);
        model.addAttribute("repImg",itemRepImgList);
        model.addAttribute("orderItem", orderViewList);
        return "/order/orderForm";
    }
}

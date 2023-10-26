package com.developer.foodfit.controller;

import com.developer.foodfit.domain.*;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.dto.order.OrderItemListResponse;
import com.developer.foodfit.dto.order.OrderItemResponse;
import com.developer.foodfit.dto.order.OrderViewResponse;
import com.developer.foodfit.service.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final UserService userService;
    private final OrderService orderService;

    /** 주문 **/
    @GetMapping("/order")
    public String order(@RequestParam(value = "id")String cartItemId, @RequestParam(value="quantity") String quantity,
                        @RequestParam(value="orderType") String orderType, Model model, Principal principal){
        String[] itemIds = cartItemId.split(",");
        String[] quantities = quantity.split(",");

        List<OrderItemResponse> orderItemList = new ArrayList<>();
        List<ItemListResponse> itemList = new ArrayList<>();
        List<ItemImgResponse> itemRepImgList = new ArrayList<>();

        for(int i=0; i<itemIds.length; i++){
            Long itemId = Long.parseLong(itemIds[i]);
            Long itemCount = Long.parseLong(quantities[i]);

            Item item = itemService.findById(itemId);

            orderItemList.add(new OrderItemResponse(itemId,itemCount));
            itemList.add(new ItemListResponse(item));
            ItemImgResponse itemImgResponse = new ItemImgResponse(itemImgService.findRepImgItemIds(item.getId()));
            itemRepImgList.add(itemImgResponse);
        }
        model.addAttribute("user",userService.findByUserId(principal.getName()));
        model.addAttribute("item", itemList);
        model.addAttribute("repImg",itemRepImgList);
        model.addAttribute("orderItem", orderItemList);
        model.addAttribute("orderType", orderType);
        return "/order/orderForm";
    }

    @GetMapping("/order/{orderId}")
    public String orderComplete(@PathVariable(value="orderId") Long orderId, Model model){
        Order order = orderService.findById(orderId);
        List<OrderItem> orderItems = orderService.findByOrderId(orderId);
//        for(OrderItem orderItem:orderItems){
//            itemList.add(orderItem)
//        }
//        List<Category> categoryList = categoryService.findByDepth(1L);
//        List<CategoryResponse> categoryResponses = categoryList.stream()
//                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
//                .collect(Collectors.toList());
//

        List<OrderItemListResponse> orderItemList = orderItems.stream()
                .map(m-> new OrderItemListResponse(m.getItem().getItemName(), m.getOrderPrice(), m.getCount()))
                .collect(Collectors.toList());

        for(int i=0; i<orderItemList.size();i++){
            System.out.println(orderItemList.get(i).getOrderItemName());
        }
        model.addAttribute("orderItem", orderItemList);
        model.addAttribute("order",new OrderViewResponse(order));
        return "/order/orderComplete";
    }
}

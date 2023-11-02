package com.developer.foodfit.controller;

import com.developer.foodfit.domain.*;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.dto.order.*;
import com.developer.foodfit.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final OrderItemService orderItemService;

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
        List<OrderItemListResponse> orderItemList = orderItems.stream()
                .map(m-> new OrderItemListResponse(m.getItem().getItemName(), m.getOrderPrice(), m.getCount()))
                .collect(Collectors.toList());

        model.addAttribute("orderItem", orderItemList);
        model.addAttribute("order",new OrderViewResponse(order));
        return "/order/orderComplete";
    }

    @GetMapping("/mypage/order")
    public String findOrderList(@PageableDefault(page=1) Pageable pageable, Principal principal, Model model){
        User user = userService.findByUserId(principal.getName());
        Page<OrderListResponse> orderList = orderService.findOrderPaging(pageable, user.getId());
        List<OrderListResponse> content = orderList.getContent();

        List<OrderViewListResponse> orderItemList = new ArrayList<>();
        List<ItemImgResponse> itemImgList = new ArrayList<>();
        for (OrderListResponse order  : content) {
            List<OrderItem> orderItems = orderItemService.findByOrderId(order.getOrderId());

            OrderItem reqItem = orderItems.get(0);
            OrderViewListResponse orderItemListResponse =
                    new OrderViewListResponse(reqItem.getOrder().getId(), reqItem.getId(), reqItem.getItem().getId(), reqItem.getItem().getItemName(), reqItem.getOrder().getOrderStatus());

            int totalPrice = orderItems.stream().mapToInt(OrderItem::getOrderPrice).sum();
            orderItemListResponse.updateTotalPrice(totalPrice);
            orderItemList.add(orderItemListResponse);

            Long itemId = orderItems.get(0).getItem().getId();
            ItemImg repImgItemIds = itemImgService.findRepImgItemIds(itemId);
            itemImgList.add(new ItemImgResponse(repImgItemIds));
        }

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), orderList.getTotalPages());

        model.addAttribute("order", orderList);
        model.addAttribute("orderItem", orderItemList);
        model.addAttribute("orderItemImg", itemImgList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/order/orderList";
    }

    @GetMapping("/order/orderDetail/{orderId}")
    public String findOrderDetail(@PathVariable Long orderId, Model model){
        Order order = orderService.findById(orderId);
        List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);

        List<OrderItemListResponse> orderItemList = orderItems.stream()
                        .map(OrderItemListResponse::new)
                        .collect(Collectors.toList());

        List<ItemImgResponse> itemImgList = orderItems.stream()
                .map(orderItem -> itemImgService.findRepImgItemIds(orderItem.getItem().getId()))
                .map(ItemImgResponse::new)
                .collect(Collectors.toList());

        model.addAttribute("orderItem", orderItemList);
        model.addAttribute("order",new OrderViewResponse(order));
        model.addAttribute("orderItemImg", itemImgList);
        return "/order/orderDetail";
    }

    @GetMapping("/order/list")
    public String findAllOrder(@PageableDefault(page=1) Pageable pageable, Model model){
        Page<OrderViewResponse> orderList = orderService.findAllPaging(pageable);
        List<OrderViewResponse> content = orderList.getContent();

        List<OrderViewListResponse> orderItemList = new ArrayList<>();
        for (OrderViewResponse order  : content) {
            List<OrderItem> orderItems = orderItemService.findByOrderId(order.getOrderId());

            OrderItem reqItem = orderItems.get(0);
            OrderViewListResponse orderItemListResponse =
                    new OrderViewListResponse(reqItem.getOrder().getId(), reqItem.getId(), reqItem.getItem().getId(), reqItem.getItem().getItemName(), reqItem.getOrder().getOrderStatus());

            int totalPrice = orderItems.stream().mapToInt(OrderItem::getOrderPrice).sum();
            orderItemListResponse.updateTotalPrice(totalPrice);
            orderItemList.add(orderItemListResponse);
        }

        //페이징
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), orderList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("order", orderList);
        model.addAttribute("orderItem", orderItemList);
        return "/order/orderManage";
    }


}

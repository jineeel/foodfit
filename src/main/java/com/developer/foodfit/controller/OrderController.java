package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final UserService userService;

    @GetMapping("/order")
    public String order(@RequestParam(value = "id")Long id, @RequestParam(value="quantity") Long quantity, Model model, Principal principal){
        Item item = itemService.findById(id);

        model.addAttribute("user",userService.findByUserId(principal.getName()));
        model.addAttribute("item", item);
        model.addAttribute("quantity", quantity);
        model.addAttribute("regImg", itemImgService.findRegImgItemId(id));
        return "/order/orderForm";
    }
}

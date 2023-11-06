package com.developer.foodfit.controller;

import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;

    /* 메인화면 */
    @GetMapping("/")
    public String index(Model model){
        List<ItemListResponse> newItemResponses = itemService.findNewItems();
        List<ItemListResponse> bestItemResponses = itemService.findBestItems();
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAllItemReqImg();

        model.addAttribute("newItem", newItemResponses);
        model.addAttribute("bestItem", bestItemResponses);
        model.addAttribute("itemImg", itemImgResponseList);
        return "main/index";
    }
}

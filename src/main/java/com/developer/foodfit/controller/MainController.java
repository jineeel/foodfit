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

    @GetMapping("/")
    public String index(Model model){
        List<ItemListResponse> newItemResponses = itemService.findNewItems();
        List<ItemListResponse> bestItemResponses = itemService.findBestItems();
<<<<<<< HEAD
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAllItemReqImg();
=======
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAllItemImg();
>>>>>>> 150101a4079d8781ff4e2a4b21b3cbc62cb4f663

        model.addAttribute("newItem", newItemResponses);
        model.addAttribute("bestItem", bestItemResponses);
        model.addAttribute("itemImg", itemImgResponseList);
        return "main/index";
    }
}

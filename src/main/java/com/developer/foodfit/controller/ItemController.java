package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.AddItemResponse;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.dto.ItemImgResponse;
import com.developer.foodfit.dto.ItemResponse;
import com.developer.foodfit.repository.ItemImgRepository;
import com.developer.foodfit.service.CategoryService;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final ItemImgService itemImgService;

    @GetMapping("/item")
    public String newItem(Model model){
        List<Category> categoryList = categoryService.findByLevel(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getId()))
                .collect(Collectors.toList());
        model.addAttribute("itemImgResponse", new AddItemResponse());
        model.addAttribute("categories", categoryResponses);
        return "item/newItem";
    }

    @GetMapping("/item/itemList")
    public String itemList(Model model){
        List<ItemResponse> itemResponseList = itemService.findAll().stream().map(ItemResponse::new).toList();
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAll().stream().map(ItemImgResponse::new).toList();
        model.addAttribute("itemImg", itemImgResponseList);
        model.addAttribute("item",itemResponseList);
        return "item/itemList";
    }

}

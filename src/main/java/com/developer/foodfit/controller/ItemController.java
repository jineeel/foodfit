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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        List<Category> categoryList = categoryService.findByDepth(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
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

    @GetMapping("/item/{categoryCode}")
    public  String findItems(@PathVariable("categoryCode")String categoryCode, Model model) throws Exception {
        System.out.println("code=="+categoryCode);
        Category category = categoryService.findByCategoryCode(categoryCode);

        String categoryName;
        List<Category> categoryList;
        List<Category> categories;
        String parentCode;

        if(category.getDepth()==1){  //1차 카테고리 일 때
            categoryName = category.getCategoryName();
            categoryList = categoryService.findByParentCode(categoryCode);
            categories = categoryList;
            parentCode = category.getCategoryCode();
        }else{
            categoryName = categoryService.findByCategoryCode(category.getParentCode()).getCategoryName();
            categoryList = categoryService.findByParentCode(category.getParentCode());
            categories = categoryService.findByCategoryCodeAndParentCode(categoryCode,category.getParentCode());
            parentCode = category.getParentCode();
        }
        List<CategoryResponse> categoryResponsesList = categoryList.stream().map(CategoryResponse::new).toList();

        List<ItemResponse> itemResponseList = new ArrayList<>();
        for(Category c: categories){
            List<Item> items = c.getItems();
            for(Item item:items){
                ItemResponse itemResponse = new ItemResponse(item);
                itemResponseList.add(itemResponse);
            }
        }
        Comparator<ItemResponse> itemIdComparator = (itemResponse1, itemResponse2) -> {
            Long itemId1 = itemResponse1.getItemId();
            Long itemId2 = itemResponse2.getItemId();
            return itemId2.compareTo(itemId1);
        };

        Collections.sort(itemResponseList,itemIdComparator);
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAll().stream().map(ItemImgResponse::new).toList();

        model.addAttribute("categoryList", categoryResponsesList);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("parentCode", parentCode);
        model.addAttribute("itemImg", itemImgResponseList);
        model.addAttribute("item", itemResponseList);

        return "item/itemList";
    }

    @GetMapping("/item/detail")
    public String detailItem(){
        return "item/itemDetail";
    }

}

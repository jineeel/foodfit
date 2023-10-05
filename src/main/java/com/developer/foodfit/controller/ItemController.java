package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.*;
import com.developer.foodfit.service.CategoryService;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/item/{categoryCode}")
    public String findItems(@PathVariable("categoryCode")String categoryCode, Model model) throws Exception {
        String categoryName = categoryService.findCategoryName(categoryCode);
        String parentCode = categoryService.findParentCode(categoryCode);
        List<Category> itemCategories = categoryService.findCategoryItemList(categoryCode);

        //카테고리 리스트
        List<CategoryResponse> categoryResponsesList = categoryService.findCategoryList(categoryCode).stream().map(CategoryResponse::new).toList();

        //카테고리별 아이템 리스트
        List<ItemListResponse> itemResponseList = itemService.findItemList(itemCategories);

        //카테고리별 아이템 이미지 리스트
        List<ItemImgListResponse> itemImgResponseList = itemImgService.findItemImgList(itemResponseList);

        model.addAttribute("categoryList", categoryResponsesList);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("parentCode", parentCode);
        model.addAttribute("itemImg", itemImgResponseList);
        model.addAttribute("item", itemResponseList);

        return "item/itemList";
    }

    @GetMapping("/item/detail/{itemId}")
    public String detailItem(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findById(itemId);
        ItemViewResponse itemViewResponse = new ItemViewResponse(item);

        List<ItemImgListResponse> itemImgList = itemImgService.findItemImgView(itemId).stream().map(ItemImgListResponse::new).toList();
        Category category = categoryService.getCategoryByCode(itemViewResponse.getCategory());

        model.addAttribute("parentName", categoryService.getCategoryByCode(category.getParentCode()).getCategoryName());
        model.addAttribute("category", category);
        model.addAttribute("itemImg", itemImgList);
        model.addAttribute("item", itemViewResponse);

        return "item/itemDetail";
    }

}

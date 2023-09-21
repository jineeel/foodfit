package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final CategoryService categoryService;

    @GetMapping("/item")
    public String newItem(Model model){
        List<Category> categoryList = categoryService.findCategory(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName()))
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryResponses);
        return "item/newItem";
    }

}

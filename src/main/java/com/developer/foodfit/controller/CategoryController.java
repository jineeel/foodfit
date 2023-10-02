package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CategoryController{

    private final CategoryService categoryService;

    @PostMapping("/item/findDetailCategory")
    @ResponseBody
    public List<CategoryResponse> findDetailCategory(@RequestParam String selectValue){

        List<Category> selectedCategory = categoryService.findByCategoryName(selectValue);
        List<CategoryResponse> categoryResponses2 = selectedCategory.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
                .collect(Collectors.toList());
        return categoryResponses2;
    }

    @PostMapping("/item/findCategories")
    public List<CategoryResponse> findCategory(){
        List<Category> categoryList = categoryService.findByDepth(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
                .collect(Collectors.toList());
        return categoryResponses;
    }
}
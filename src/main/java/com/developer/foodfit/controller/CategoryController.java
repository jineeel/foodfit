package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.dto.item.CategoryResponse;
import com.developer.foodfit.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CategoryController{

    private final CategoryService categoryService;

    /* 2차 카테고리 조회 */
    @PostMapping("/api/item/findDetailCategory")
    public ResponseEntity<List<CategoryResponse>> findDetailCategory(@RequestBody String selectValue){
        List<Category> selectedCategory = categoryService.findByCategoryName(selectValue);
        List<CategoryResponse> categoryResponses2 = selectedCategory.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryResponses2);
    }

    /* 카테고리 조회 */
    @PostMapping("/api/item/findCategories")
    public ResponseEntity<List<CategoryResponse>> findCategory(){
        List<Category> categoryList = categoryService.findByDepth(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryResponses);
    }
}
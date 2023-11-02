package com.developer.foodfit.dto.item;

import com.developer.foodfit.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String parentCode;
    private Long depth;

    public CategoryResponse(String categoryName, String categoryCode, Long depth) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.depth = depth;
    }

    public CategoryResponse(Category category) {
        this.categoryId=category.getId();
        this.categoryCode=category.getCategoryCode();
        this.categoryName=category.getCategoryName();
        this.parentCode=category.getParentCode();
    }
}

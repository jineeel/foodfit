package com.developer.foodfit.dto;

import com.developer.foodfit.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryResponse {
    private String categoryName;
    private String categoryCode;
    private String parentCode;
    private Long level;

    public CategoryResponse(String categoryName, String categoryCode) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }
}

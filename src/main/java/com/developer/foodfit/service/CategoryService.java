package com.developer.foodfit.service;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findByCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Category> findByDepth(Long depth) {
        List<Category> categories= categoryRepository.findByDepth(depth);
        return categories;
    }

    public List<Category> findByCategoryName(String selectedOption) {
        Category category = categoryRepository.findByCategoryName(selectedOption);
        List<Category> categories = categoryRepository.findByParentCodeAndDepth(category.getCategoryCode(),2L);
        return categories;
    }

    public List<Category> findByParentCode(String parentCode) {
        return categoryRepository.findByParentCode(parentCode);
    }

    public List<Category> findByCategoryCodeAndParentCode(String categoryCode, String parentCode) {
        return categoryRepository.findByCategoryCodeAndParentCode(categoryCode,parentCode);
    }

    public Category findByCategoryCode(String categoryCode){
        return categoryRepository.findByCategoryCode(categoryCode);
    }
}

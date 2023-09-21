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

    public List<Category> findCategory(Long level) {
        List<Category> categories= categoryRepository.findByLevel(level);
        return categories;
    }

    public List<Category> findSelectedCategory(String selectedOption) {
        Category category = categoryRepository.findByCategoryName(selectedOption);
        List<Category> categories = categoryRepository.findByParentCodeAndLevel(category.getCategoryCode(),2L);
        return categories;
    }
}

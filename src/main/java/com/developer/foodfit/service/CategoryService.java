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

//    public Category findByCategory(Long id) {
//        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
//    }

    public List<Category> findByDepth(Long depth) {
        List<Category> categories= categoryRepository.findByDepth(depth);
        return categories;
    }

    public List<Category> findByCategoryName(String selectedOption) {
        Category category = categoryRepository.findByCategoryName(selectedOption);
        List<Category> categories = categoryRepository.findByParentCodeAndDepth(category.getCategoryCode(),2L);
        return categories;
    }

//    public List<Category> findByParentCode(String parentCode) {
//        return categoryRepository.findByParentCode(parentCode);
//    }
//
//    public List<Category> findByCategoryCodeAndParentCode(String categoryCode, String parentCode) {
//        return categoryRepository.findByCategoryCodeAndParentCode(categoryCode,parentCode);
//    }
//
//    public Category findByCategoryCode(String categoryCode){
//        return categoryRepository.findByCategoryCode(categoryCode);
//    }

    //카테고리 리스트 조회
    public List<Category> findCategoryList(String categoryCode) {
        Category category = getCategoryByCode(categoryCode);
        String code = category.getDepth() == 1 ? categoryCode : category.getParentCode();
        return categoryRepository.findByParentCode(code);
    }

    //아이템용 카테고리 리스트 조회
    public List<Category> findCategoryItemList(String categoryCode) {
        Category category = getCategoryByCode(categoryCode);
        return category.getDepth() == 1 ?
                categoryRepository.findByParentCode(categoryCode) :
                categoryRepository.findByCategoryCodeAndParentCode(categoryCode, category.getParentCode());
    }

    //카테고리 이름 조회
    public String findCategoryName(String categoryCode) {
        Category category = getCategoryByCode(categoryCode);
        return category.getDepth() == 1 ?
                category.getCategoryName() :
                categoryRepository.findByCategoryCode(category.getParentCode()).getCategoryName();
    }

    //카테고리 최상위 ParentCode 조회
    public String findParentCode(String categoryCode) {
        Category category = getCategoryByCode(categoryCode);
        return category.getDepth() == 1 ? categoryCode : category.getParentCode();
    }

    //카테고리코드 조회
    public Category getCategoryByCode(String categoryCode){
        return categoryRepository.findByCategoryCode(categoryCode);
    }

}

package com.developer.foodfit.repository;

import com.developer.foodfit.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByDepth(long depth);
    Category findByCategoryName(String categoryName);
    Category findByCategoryCode(String categoryCode);
    List<Category> findByCategoryCodeAndParentCode(String categoryCode, String parentCode);
    List<Category> findByParentCode(String parentCode);
    List<Category> findByParentCodeAndDepth(String categoryCode, long level);

}

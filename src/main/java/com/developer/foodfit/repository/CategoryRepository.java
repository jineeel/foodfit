package com.developer.foodfit.repository;

import com.developer.foodfit.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByLevel(long level);

    Category findByCategoryName(String categoryName);
    Category findByCategoryCode(String categoryCode);
    Category findByParentCode(String parentCode);
    List<Category> findByParentCodeAndLevel(String categoryCode, long level);
}

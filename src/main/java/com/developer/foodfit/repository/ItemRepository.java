package com.developer.foodfit.repository;


import com.developer.foodfit.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByCategoryId(Long categoryId);
    List<Item> findTop20ByOrderByIdDesc();
    List<Item> findTop20ByOrderByItemSellCountDesc();
    List<Item> findTop20ByOrderByCalorieAsc();
    List<Item> findTop10ByOrderByIdDesc();
    List<Item> findTop10ByOrderByItemSellCountDesc();
}

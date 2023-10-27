package com.developer.foodfit.repository;


import com.developer.foodfit.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
//    Page<Item> findByCategoryId(Long categoryId, PageRequest id);
    List<Item> findByCategoryId(Long categoryId);
//    List<Item> findByIdOrderByIdDesc(Long itemId);
////    Page<Item> findById(Long itemId, PageRequest id);
}

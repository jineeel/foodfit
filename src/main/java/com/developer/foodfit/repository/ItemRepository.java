package com.developer.foodfit.repository;


import com.developer.foodfit.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByCategoryId(Long id);
//    List<Item> findByCategoryCategoryCode(String categoryCode);

//    List<Item> findByCategoryParentCode(String categoryCode);
//    Item findByCategoryParentCode(String categoryCode);
}

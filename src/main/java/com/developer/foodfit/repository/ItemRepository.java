package com.developer.foodfit.repository;


import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findAllByItemSellStatusNot(Pageable pageable,ItemSellStatus itemSellStatus);
    List<Item> findByCategoryIdAndItemSellStatusNot(Long categoryId, ItemSellStatus itemSellStatus);
    List<Item> findTop20ByItemSellStatusNotOrderByIdDesc(ItemSellStatus itemSellStatus);
    List<Item> findTop20ByItemSellStatusNotOrderByItemSellCountDesc(ItemSellStatus itemSellStatus);
    List<Item> findTop20ByItemSellStatusNotOrderByCalorieAsc(ItemSellStatus itemSellStatus);
    List<Item> findTop10ByItemSellStatusNotOrderByIdDesc(ItemSellStatus itemSellStatus);
    List<Item> findTop10ByItemSellStatusNotOrderByItemSellCountDesc(ItemSellStatus itemSellStatus);
}

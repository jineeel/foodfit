package com.developer.foodfit.repository;


import com.developer.foodfit.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item,Long> {

}

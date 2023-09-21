package com.developer.foodfit.domain;

import jakarta.persistence.*;

@Entity
public class CategoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_item_id")
    private Long id;

    //@OneToMany

  //  private Category category;
}

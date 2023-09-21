package com.developer.foodfit.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private Long level;

    @Column(name="parent_code")
    private String parentCode;

    @Column(name="category_code")
    private String categoryCode;

    @Column(name="category_name")
    private String categoryName;


}

package com.developer.foodfit.domain;

import com.developer.foodfit.constant.ItemSellStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;
    private String itemName;
    private int price;
    private int stockNumber;

    @Lob
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @OneToOne
    @JoinColumn(name="category_code")
    private Category category;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
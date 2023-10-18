package com.developer.foodfit.domain;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.dto.item.UpdateItemRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id", updatable = false)
    private Long id;

    private String itemName;

    private int price;

    private int stockNumber;

    @Lob
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String author;

    @Builder
    public Item(Long id, String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus, Category category,
                 LocalDateTime createDate, LocalDateTime updateDate, String author, String author1) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.category = category;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.author = author;
    }

    public void update(String author, UpdateItemRequest request, Category category) {
        this.itemName = request.getItemName();
        this.price = request.getPrice();
        this.stockNumber = request.getStockNumber();
        this.itemDetail = request.getItemDetail();
        this.itemSellStatus = request.getItemSellStatus();
        this.updateDate = LocalDateTime.now();
        this.category = category;
        this.author = author;
    }
}
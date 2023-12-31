package com.developer.foodfit.domain;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.dto.item.UpdateItemRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String itemName;

    @NotNull
    private int price;

    @NotNull
    private int stockNumber;

    @Lob
    @NotNull
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemSellStatus itemSellStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    private LocalDateTime updateDate;

    @NotNull
    private String author;

    private int itemSellCount;

    @NotNull
    private int calorie;

    @Builder
    public Item(Long id, String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus, Category category,
                 LocalDateTime createDate, LocalDateTime updateDate, String author, int calorie) {
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
        this.calorie = calorie;
    }

    public void update(String author, UpdateItemRequest request, Category category) {
        this.itemName = request.getItemName();
        this.price = request.getPrice();
        this.stockNumber = request.getStockNumber();
        this.itemDetail = request.getItemDetail();
        this.updateDate = LocalDateTime.now();
        this.category = category;
        this.author = author;
        this.calorie = request.getCalorie();
    }

    public void updateStockNumber(int stockNumber){
        this.stockNumber = this.stockNumber-stockNumber;
    }
    public void updateItemStatus(ItemSellStatus itemSellStatus){
        this.itemSellStatus = itemSellStatus;
    }
    public void updateItemSellCount(int itemSellCount){
        this.itemSellCount += itemSellCount;
    }
}
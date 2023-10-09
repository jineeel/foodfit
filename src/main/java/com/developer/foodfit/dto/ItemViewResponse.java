package com.developer.foodfit.dto;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ItemViewResponse {
    private Long itemId;
    private String itemName;
    private int price;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
    private String category;
    private String categoryName;
    private String itemDetail;


    public ItemViewResponse(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.itemSellStatus = item.getItemSellStatus();
        this.category = item.getCategory().getCategoryCode();
        this.categoryName = item.getCategory().getCategoryName();
        this.itemDetail = item.getItemDetail();
        this.stockNumber = item.getStockNumber();
    }
}


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
@ToString
public class ItemResponse {

    private Long itemId;
    private String itemName;
    private int price;
    private ItemSellStatus itemSellStatus;
    private String category;


    public ItemResponse(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.itemSellStatus = item.getItemSellStatus();
        this.category = item.getCategory().getCategoryName();
    }
}

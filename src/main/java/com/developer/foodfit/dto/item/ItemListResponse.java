package com.developer.foodfit.dto.item;

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
public class ItemListResponse {

    private Long itemId;
    private String itemName;
    private int price;
    private ItemSellStatus itemSellStatus;
    private String category;
    private int stockNumber;

    public ItemListResponse(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.itemSellStatus = item.getItemSellStatus();
        this.category = item.getCategory().getCategoryName();
        this.stockNumber = item.getStockNumber();
    }

}

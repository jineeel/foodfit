package com.developer.foodfit.dto;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ItemResponse {

    private String itemName;
    private int price;
    private ItemSellStatus itemSellStatus;
    private String category;

}

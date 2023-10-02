package com.developer.foodfit.dto;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddItemRequest {
    private String itemName;
    private int price;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
    private String itemDetail;
    private String category;
    private LocalDateTime createDate;
    private String author;

}

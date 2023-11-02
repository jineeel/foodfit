package com.developer.foodfit.dto.item;

import com.developer.foodfit.constant.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateItemRequest {
    private Long id;
    private String itemName;
    private int price;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
    private String itemDetail;
    private String category;
    private LocalDateTime updateDate;
    private String author;
    private int calorie;
}

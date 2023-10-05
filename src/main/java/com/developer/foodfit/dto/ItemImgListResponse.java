package com.developer.foodfit.dto;

import com.developer.foodfit.domain.ItemImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgListResponse {
    private String imgUrl;
    private String imgName;
    private String repImgYn;
    private Long itemId;

    public ItemImgListResponse(ItemImg itemImg) {
        this.imgUrl = itemImg.getImgUrl();
        this.imgName = itemImg.getImgName();
        this.repImgYn = itemImg.getRepImgYn();
        this.itemId = itemImg.getItem().getId();
    }

}

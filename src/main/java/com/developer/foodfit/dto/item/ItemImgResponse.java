package com.developer.foodfit.dto.item;

import com.developer.foodfit.domain.ItemImg;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemImgResponse {
    private Long id;
    private String imgUrl;
    private String imgName;
    private String repImgYn;
    private Long itemId;
    private String orgImgName;

    public ItemImgResponse(ItemImg itemImg) {
        this.imgUrl = itemImg.getImgUrl();
        this.imgName = itemImg.getImgName();
        this.repImgYn = itemImg.getRepImgYn();
        this.itemId = itemImg.getItem().getId();
        this.orgImgName = itemImg.getOrgImgName();
    }


}

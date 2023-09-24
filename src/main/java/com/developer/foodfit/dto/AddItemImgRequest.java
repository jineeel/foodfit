package com.developer.foodfit.dto;

import com.developer.foodfit.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AddItemImgRequest {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AddItemImgRequest of(ItemImg itemImg) {
        return modelMapper.map(itemImg, AddItemImgRequest.class);
    }
}

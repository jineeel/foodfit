package com.developer.foodfit.dto;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddItemResponse {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private Integer stockNumber;
    private ItemSellStatus itemSellStatus;

    //상품 저장 후 수정할때 상품 이미지 정보를 저장하는 리스트
    private List<AddItemImgRequest> itemImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this,Item.class);
    }
    public static AddItemResponse of(Item item){
        return modelMapper.map(item, AddItemResponse.class);
    }
}

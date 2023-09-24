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

    @NotBlank(message = "상품명은 필수 입력 값입니다")
    private String itemNm;

    @NotNull(message="가격은 필수 입력 값입니다")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message="재고는 필수 입력 값입니다")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    //상품 저장 후 수정할때 상품 이미지 정보를 저장하는 리스트
    private List<AddItemImgRequest> itemImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    //modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Item createItem(){
        return modelMapper.map(this,Item.class);
    }
    public static AddItemResponse of(Item item){
        return modelMapper.map(item, AddItemResponse.class);
    }
}

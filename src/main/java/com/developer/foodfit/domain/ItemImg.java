package com.developer.foodfit.domain;

import com.developer.foodfit.constant.ItemSellStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ServiceLoader;

@Entity
@Getter
@Setter
public class ItemImg{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="item_img_id")
    private Long id;

    @NotNull
    private String imgName; //이미지 파일명

    @NotNull
    private String orgImgName; //원본 이미지 파일명

    @NotNull
    private String imgUrl; //이미지 조회 경로

    @NotNull
    private String repImgYn;  //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY) //상품 엔티티와 다대일 단방향 관계로 매핑/ 매핑된 상품 엔티티 정보가 필요한 경우 데이터를 조회
    @JoinColumn(name="item_id")
    @NotNull
    private Item item;

    public void updateItemImg(String orgImgName, String imgName, String imgUrl){
        this.orgImgName = orgImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}


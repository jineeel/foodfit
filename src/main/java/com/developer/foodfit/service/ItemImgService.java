package com.developer.foodfit.service;

import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.ItemImgResponse;
import com.developer.foodfit.dto.ItemResponse;
import com.developer.foodfit.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl ="/images/item/" + imgName;
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }


    public List<ItemImg> findAll() {
        return itemImgRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
       // return itemImgRepository.findByItemIdAndRepImgYn(1L,"Y");//
    }


    public List<ItemImg> findAllByItemId(Long itemId) {
        return itemImgRepository.findAllByItemId(itemId);
    }

    //카테고리별 아이템 이미지 리스트
    public List<ItemImgResponse> findItemImg(List<ItemResponse> items) {
        return items.stream()
                .map(ItemResponse::getItemId)
                .flatMap(itemId -> itemImgRepository.findItemImgByItemId(itemId).stream())
                .map(ItemImgResponse::new)
                .collect(Collectors.toList());
    }

//    public List<ItemImgResponse> findItemImg(List<ItemResponse> items){
//        List<ItemImgResponse> itemImgResponseList = new ArrayList<>();
//
//        for(ItemResponse i: items){
//            List<ItemImg> itemImgList = itemImgRepository.findItemImgByItemId(i.getItemId());
//            for(ItemImg itemImg : itemImgList){
//                ItemImgResponse itemImgResponse = new ItemImgResponse(itemImg);
//                itemImgResponseList.add(itemImgResponse);
//            }
//        }
//        return itemImgResponseList;
//    }




}


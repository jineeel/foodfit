package com.developer.foodfit.service;

import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.ItemImgResponse;
import com.developer.foodfit.dto.ItemListResponse;
import com.developer.foodfit.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

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

    /** 상품 이미지 저장 **/
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
    }

    public List<ItemImg> findAllByItemId(Long itemId) {
        return itemImgRepository.findAllByItemId(itemId);
    }

    public List<ItemImg> findItemImgView(Long itemId){
        return itemImgRepository.findItemImgByItemId(itemId);
    }

    /** 카테고리별 상품 이미지 리스트 **/
    public List<ItemImgResponse> findItemImgList(List<ItemListResponse> items) {
        return items.stream()
                .map(ItemListResponse::getItemId)
                .flatMap(itemId -> itemImgRepository.findItemImgByItemId(itemId).stream())
                .map(ItemImgResponse::new)
                .collect(Collectors.toList());
    }

    public List<ItemImg> findItemImg(Long itemId) {
        return itemImgRepository.findItemImgByItemId(itemId);
    }

    /** 상품 이미지 업데이트 **/
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(()->new IllegalArgumentException());

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

}


package com.developer.foodfit.service;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ItemImg> findItemImgView(Long itemId){
        return itemImgRepository.findItemImgByItemId(itemId);
    }

    /** 아이템 대표 이미지 **/
    public String findRepImgItemId(Long itemId){
        List<ItemImg> itemImg = itemImgRepository.findItemImgByItemId(itemId);
        return itemImg.stream()
                .filter(img -> "Y".equals(img.getRepImgYn()))
                .map(ItemImg::getImgUrl)
                .findFirst()
                .orElse("");
    }

    /** 아이템 대표 이미지 리스트 **/
    public List<ItemImgResponse> findRepImgListItemId(Long itemId) {
        return itemImgRepository.findItemImgByItemId(itemId).stream()
                .filter(img -> "Y".equals(img.getRepImgYn()))
                .map(ItemImgResponse::new)
                .findFirst()
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    public Page<ItemImgResponse> paging(Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 4;

        Page<ItemImg> itemImgPages =itemImgRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<ItemImgResponse> itemImgListResponses = itemImgPages.map(itemImgPage-> new ItemImgResponse(itemImgPage));
        return  itemImgListResponses;
    }

    public List<ItemImgResponse> findAllItemImg(){
        List<ItemImg> itemImg = itemImgRepository.findAll();
        List<ItemImgResponse> itemListResponses = itemImg.stream().map(m->new ItemImgResponse(m)).collect(Collectors.toList());
        return itemListResponses;
    }

    public ItemImg findRepImgItemIds(Long itemId) {
        return itemImgRepository.findByItemIdAndRepImgYn(itemId,"Y");
    }

    /** 카테고리별 상품 이미지 리스트 **/
    public List<ItemImgResponse> findItemImgList(Page<ItemListResponse> items) {
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

    public List<ItemImgResponse> findNewImgList() {
        return itemImgRepository.findTop10ByOrderByIdDesc().stream()
                .map(ItemImgResponse::new)
                .collect(Collectors.toList());

    }
//    public List<ItemImgResponse> findBestImgList() {
//        return itemImgRepository.findTop10ByOrderByItemSellStatusDesc().stream()
//                .map(ItemImgResponse::new)
//                .collect(Collectors.toList());
//
//    }


}


package com.developer.foodfit.service;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.item.AddItemRequest;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.dto.item.UpdateItemRequest;
import com.developer.foodfit.repository.CategoryRepository;
import com.developer.foodfit.repository.ItemImgRepository;
import com.developer.foodfit.repository.ItemRepository;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;
    private final UserRepository userRepository;

    /** 상품 저장 **/
    public Item save(AddItemRequest request, String name, List<MultipartFile> itemImgFileList) throws Exception {
        Category category = categoryRepository.findByCategoryCode(request.getCategory());
        Item item = Item.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .stockNumber(request.getStockNumber())
                .itemDetail(request.getItemDetail())
                .itemSellStatus(ItemSellStatus.SELL)
                .category(category)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .author(name)
                .build();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            MultipartFile image = itemImgFileList.get(i);
            if(image.getOriginalFilename().equals("")){
                break;
            }
            if(i == 0)
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item;
    }

    /** 카테고리 상품 조회 **/
    public List<ItemListResponse> findItemList(List<Category> categories) {
        return categories.stream()
                .flatMap(category -> category.getItems().stream())
                .map(ItemListResponse::new)
                .sorted(Comparator.comparing(ItemListResponse::getItemId).reversed())
                .collect(Collectors.toList());
    }

    public Item findById(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(()-> new IllegalArgumentException(""));
    }

    public List<Item> findItem(Long itemId){
        Optional<Item> item = itemRepository.findById(itemId);

        return item.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    /** 상품 수정 **/
    @Transactional
    public Item update(Long id, String author, List<MultipartFile> itemImgFileList, UpdateItemRequest request) throws Exception {
        Category category = categoryRepository.findByCategoryCode(request.getCategory());
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(""));
        authorizeItemRole();
        item.update(author,request,category);

        List<ItemImg> itemImgList = itemImgRepository.findAllByItemId(request.getId());

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            if (i < itemImgList.size() && itemImgList.get(i) != null) {
                itemImgService.updateItemImg(itemImgList.get(i).getId(), itemImgFileList.get(i));
            } else {
                ItemImg itemImg = new ItemImg();
                itemImg.setItem(item);
                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            }
        }
        return item;
    }

    /** 상품 삭제 **/
    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(""));
        List<ItemImg> itemImgList = itemImgRepository.findItemImgByItemId(item.getId());
        authorizeItemRole();

        for(int i=0; i<itemImgList.size(); i++){
            itemImgRepository.delete(itemImgList.get(i));
        }
        itemRepository.delete(item);
    }

    //권한 확인
    private void authorizeItemRole(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = userRepository.findByUserId(userName).orElseThrow(()-> new IllegalArgumentException("")).getRole().getKey();
        if(!(userRole=="ROLE_GUEST")){
            throw new IllegalArgumentException("not authorized");
        }
    }
}

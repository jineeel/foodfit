package com.developer.foodfit.service;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.AddItemRequest;
import com.developer.foodfit.dto.ItemListResponse;
import com.developer.foodfit.repository.CategoryRepository;
import com.developer.foodfit.repository.ItemImgRepository;
import com.developer.foodfit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


    //카테고리별 아이템 조회
    public List<ItemListResponse> findItemList(List<Category> categories) {
        return categories.stream()
                .flatMap(category -> category.getItems().stream())
                .map(ItemListResponse::new)
                .sorted(Comparator.comparing(ItemListResponse::getItemId).reversed())
                .collect(Collectors.toList());
    }
//    public List<ItemResponse> findItemList(List<Category> categories){
//        List<ItemResponse> itemResponseList = new ArrayList<>();
//
//        for(Category c: categories){
//            List<Item> items = c.getItems();
//            for(Item item:items){
//                ItemResponse itemResponse = new ItemResponse(item);
//                itemResponseList.add(itemResponse);
//            }
//        }
//        Comparator<ItemResponse> itemIdComparator = (itemResponse1, itemResponse2) -> {
//            Long itemId1 = itemResponse1.getItemId();
//            Long itemId2 = itemResponse2.getItemId();
//            return itemId2.compareTo(itemId1);
//        };
//        Collections.sort(itemResponseList,itemIdComparator);
//
//        return itemResponseList;
//    }

    public Item findById(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(()-> new IllegalArgumentException(""));
    }
}

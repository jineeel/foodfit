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
import org.springframework.data.domain.*;
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
                .calorie(request.getCalorie())
                .itemDetail(request.getItemDetail())
                .category(category)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .author(name)
                .build();
        itemRepository.save(item);
        setItemSellStatus(item, request.getStockNumber());

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

    /** 카테고리별 상품 조회 **/
    public Page<ItemListResponse> findItemPaging(Pageable pageable, List<Category> categories){
        int page = pageable.getPageNumber()-1;
        int pageLimit = 8;
        List<Item> allItems = new ArrayList<>();

        for(Category category : categories){
            List<Item> itemList = itemRepository.findByCategoryIdAndItemSellStatusNot(category.getId(),ItemSellStatus.DELETE);
            allItems.addAll(itemList);
        }
        allItems.sort(Comparator.comparingLong(Item::getId).reversed());

        int start = (page) * pageLimit;
        int end = Math.min(start + pageLimit, allItems.size());

        pageable = PageRequest.of(page, pageLimit);
        Page<Item> itemPage = new PageImpl<>(allItems.subList(start,end), pageable, allItems.size());
        Page<ItemListResponse> itemListResponses = itemPage.map(ItemListResponse::new);
        return itemListResponses;
    }

    /** 전체 상품 조회 **/
    public Page<ItemListResponse> findAll(Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 8;

        Page<Item> itemPages =itemRepository.findAllByItemSellStatusNot(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")),ItemSellStatus.DELETE);
        Page<ItemListResponse> itemListResponses = itemPages.map(itemPage-> new ItemListResponse(itemPage));
        return  itemListResponses;
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
        setItemSellStatus(item, request.getStockNumber());

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
    /** 상품 수량 + 상품 판매 상태 수정 **/
    @Transactional
    public Item updateItemCount(Long id, int stockNumber){
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(""));
        item.updateStockNumber(stockNumber);
        if(item.getStockNumber()<=0){
            item.updateItemStatus(ItemSellStatus.SOLD_OUT);
        }
        return item;
    }

    /** 상품 삭제 **/
    @Transactional
    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(""));
        authorizeItemRole();
        item.updateItemStatus(ItemSellStatus.DELETE);
    }

    //권한 확인
    private void authorizeItemRole(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = userRepository.findByUserId(userName).orElseThrow(()-> new IllegalArgumentException("")).getRole().getKey();
        if(!(userRole=="ROLE_ADMIN")){
            throw new IllegalArgumentException("not authorized");
        }
    }
    //상품 상태
    public void setItemSellStatus(Item item, int stockNumber){
        if(stockNumber>0){
            item.updateItemStatus(ItemSellStatus.SELL);
        }else{
            item.updateItemStatus(ItemSellStatus.SOLD_OUT);
        }
    }

    public List<ItemListResponse> findTopItems(String itemCode){
        List<Item> itemList = new ArrayList<>();
        if(itemCode.equals("new")){
            itemList = itemRepository.findTop20ByItemSellStatusNotOrderByIdDesc(ItemSellStatus.DELETE);
        }else if(itemCode.equals("best")) {
            itemList = itemRepository.findTop20ByItemSellStatusNotOrderByItemSellCountDesc(ItemSellStatus.DELETE);
        }else if(itemCode.equals("calorie")){
            itemList = itemRepository.findTop20ByItemSellStatusNotOrderByCalorieAsc(ItemSellStatus.DELETE);
        }
        List<ItemListResponse> itemListResponses = itemList.stream()
                .map(ItemListResponse::new)
                .collect(Collectors.toList());
        return itemListResponses;
    }

    public List<ItemListResponse> findNewItems(){
        return itemRepository.findTop10ByItemSellStatusNotOrderByIdDesc(ItemSellStatus.DELETE).stream().map(ItemListResponse::new).collect(Collectors.toList());
    }
    public List<ItemListResponse> findBestItems(){
        return itemRepository.findTop10ByItemSellStatusNotOrderByItemSellCountDesc(ItemSellStatus.DELETE).stream().map(ItemListResponse::new).collect(Collectors.toList());
    }

}

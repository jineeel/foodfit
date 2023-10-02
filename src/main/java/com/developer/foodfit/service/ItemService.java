package com.developer.foodfit.service;

import com.developer.foodfit.constant.ItemSellStatus;
import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.domain.ItemImg;
import com.developer.foodfit.dto.AddItemRequest;
import com.developer.foodfit.repository.CategoryRepository;
import com.developer.foodfit.repository.ItemImgRepository;
import com.developer.foodfit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Item save(AddItemRequest request, String name, List<MultipartFile> itemImgFileList) throws Exception {
        System.out.println(request.getCategory()+"!!!");

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

    public List<Item> findAll() {
        return itemRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

//    public List<Item> findCategoryItem(String categoryCode){
//
//        List<Item> byCategoryParentCode = itemRepository.findByCategoryParentCode(categoryCode);
//        System.out.println("!!"+byCategoryParentCode.size());
//        return byCategoryParentCode;
//    }
}

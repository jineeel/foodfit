package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.dto.AddItemRequest;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("/api/item")
    public ResponseEntity<Item> createItem(AddItemRequest request, Principal principal,@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {
        Item saved = itemService.save(request, principal.getName(),itemImgFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

//    @GetMapping("/api/item/{categoryCode}")
//    public ResponseEntity<List<Item>> findItems(@PathVariable("categoryCode")String categoryCode){
//        System.out.println("???"+categoryCode);
//        List<Item> all = itemService.findCategoryId(categoryCode);
//        for(int i =0; i<all.size(); i++){
//            System.out.println("???"+all.get(i));
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(all);
//    }
}

package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.dto.AddItemRequest;
import com.developer.foodfit.dto.UpdateItemRequest;
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

    /** 상품 등록 **/
    @PostMapping("/api/item")
    public ResponseEntity<Item> createItem(AddItemRequest request, Principal principal,@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {
        Item saveItem = itemService.save(request, principal.getName(),itemImgFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveItem);
    }

    /** 상품 수정 **/
    @PutMapping("/api/item/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, Principal principal, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList, UpdateItemRequest request) throws Exception {
        Item updateItem = itemService.update(id,principal.getName(),itemImgFileList,request);
        return ResponseEntity.ok().body(updateItem);
    }

}

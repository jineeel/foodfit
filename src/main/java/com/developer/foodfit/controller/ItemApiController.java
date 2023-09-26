package com.developer.foodfit.controller;

import com.developer.foodfit.domain.Item;
import com.developer.foodfit.dto.AddItemRequest;
import com.developer.foodfit.dto.CategoryResponse;
import com.developer.foodfit.dto.ItemResponse;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("/api/item")
    public ResponseEntity<Item> createItem(AddItemRequest request, Principal principal,@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {
        Item saved = itemService.save(request, principal.getName(),itemImgFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/api/item/{categoryId}")
    public ResponseEntity<List<Item>> findItems(@PathVariable("categoryId")Long categoryId){
        List<Item> all = itemService.findCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
}

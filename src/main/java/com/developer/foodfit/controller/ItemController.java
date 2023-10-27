package com.developer.foodfit.controller;

import com.developer.foodfit.config.oauth.OAuth2LoginFailureHandler;
import com.developer.foodfit.domain.Category;
import com.developer.foodfit.domain.Item;
import com.developer.foodfit.dto.*;
import com.developer.foodfit.dto.item.ItemImgResponse;
import com.developer.foodfit.dto.item.ItemListResponse;
import com.developer.foodfit.dto.item.ItemViewResponse;
import com.developer.foodfit.service.CategoryService;
import com.developer.foodfit.service.ItemImgService;
import com.developer.foodfit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final ItemImgService itemImgService;

    /** 상품 등록 & 수정 **/
    @GetMapping("/item")
    public String newItem(@RequestParam(value = "id", required = false)Long id, Model model){
        List<Category> categoryList = categoryService.findByDepth(1L);
        List<CategoryResponse> categoryResponses = categoryList.stream()
                .map(m-> new CategoryResponse(m.getCategoryName(), m.getCategoryCode(), m.getDepth()))
                .collect(Collectors.toList());
        ItemViewResponse itemViewResponse;

        if(id==null){   //상품 등록
            model.addAttribute("item", new ItemViewResponse());
        }else{  //상품 수정
            Item item = itemService.findById(id);
            itemViewResponse = new ItemViewResponse(item);
            List<ItemImgResponse> itemImg = itemImgService.findItemImg(item.getId())
                    .stream().map(ItemImgResponse::new).toList();
            model.addAttribute("itemImg",itemImg);
            model.addAttribute("item", itemViewResponse);
            model.addAttribute("parentName", categoryService.findParentName(itemViewResponse.getCategory()));
        }

        model.addAttribute("categories", categoryResponses);
        return "item/itemForm";
    }

    /** 카테고리 상품 조회 **/
    @GetMapping("/item/list/{categoryCode}")
    public String findItems(@PathVariable("categoryCode")String categoryCode, @PageableDefault(page=1) Pageable pageable, Model model) throws Exception {
        List<Category> categoryItemList = categoryService.findCategoryItemList(categoryCode);
        String categoryName = categoryService.findCategoryName(categoryCode);
        String parentCode = categoryService.findParentCode(categoryCode);

        //카테고리 리스트
        List<CategoryResponse> categoryResponsesList = categoryService.findCategoryList(categoryCode).stream().map(CategoryResponse::new).toList();

        //카테고리별 아이템 리스트
        Page<ItemListResponse> itemResponseList = itemService.findItemPaging(pageable, categoryItemList);

        //카테고리별 아이템 이미지 리스트
        List<ItemImgResponse> itemImgResponseList = itemImgService.findItemImgList(itemResponseList);

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), itemResponseList.getTotalPages());

        model.addAttribute("categoryList", categoryResponsesList);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("parentCode", parentCode);
        model.addAttribute("itemImg", itemImgResponseList);
        model.addAttribute("itemPages", itemResponseList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "item/itemList";
    }
    /** 전체 상품 조회 **/
    @GetMapping("/item/list")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        Page<ItemListResponse> itemResponseList = itemService.findAll(pageable);
        List<ItemImgResponse> itemImgResponseList = itemImgService.findAllItemImg();

        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), itemResponseList.getTotalPages());

        model.addAttribute("itemPages", itemResponseList);
        model.addAttribute("itemImg", itemImgResponseList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("allCategory", true);

        return "item/itemList";
    }

    /** 상품 상세보기 **/
    @GetMapping("/item/detail/{itemId}")
    public String detailItem(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findById(itemId);
        ItemViewResponse itemViewResponse = new ItemViewResponse(item);

        List<ItemImgResponse> itemImgList = itemImgService.findItemImgView(itemId).stream().map(ItemImgResponse::new).toList();
        Category category = categoryService.getCategoryByCode(itemViewResponse.getCategory());

        model.addAttribute("parentName", categoryService.getCategoryByCode(category.getParentCode()).getCategoryName());
        model.addAttribute("category", category);
        model.addAttribute("itemImg", itemImgList);
        model.addAttribute("item", itemViewResponse);

        return "item/itemDetail";
    }


}

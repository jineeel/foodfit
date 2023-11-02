package com.developer.foodfit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {

//    @GetMapping("/board/notice")
//    public String notice(){
//        return "board/notice";
//    }
    @GetMapping("/board/exercise")
    public String exercise(){
        return "board/exercise";
    }
}

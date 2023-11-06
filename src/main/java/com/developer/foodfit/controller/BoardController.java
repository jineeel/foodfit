package com.developer.foodfit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {

    /* 홈트레이닝 페이지 */
    @GetMapping("/board/exercise")
    public String exercise(){
        return "board/exercise";
    }
}

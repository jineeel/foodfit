package com.developer.foodfit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminController {

    /* 관리자 페이지 */
    @GetMapping("/admin")
    public String admin(){
        return "/admin/adminForm";
    }
}

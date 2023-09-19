package com.developer.foodfit.controller;

import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    @GetMapping("user/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("user/join")
    public String join(){
        return "user/join";
    }

}

package com.developer.foodfit.controller;

import com.developer.foodfit.dto.AddUserRequest;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/join")
    public String join(AddUserRequest request){
        userService.save(request);
        return "redirect:/user/login";
    }

    /**
     * 아이디 중복 체크
     * @param userId
     * @return userId가 유무에 따른 boolean 값
     */
    @PostMapping("/user/checkId")
    @ResponseBody
    public boolean checkUserId(@RequestParam("userId") String userId){
        return userService.checkUserId(userId);
    }
}

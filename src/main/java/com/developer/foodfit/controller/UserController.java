package com.developer.foodfit.controller;

import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.AddUserRequest;
import com.developer.foodfit.dto.UpdateUserRequest;
import com.developer.foodfit.dto.ViewUserResponse;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/user/signup")
    public String join(){
        return "user/signup";
    }

    @GetMapping("/mypage")
    public String mypage(){
        return "user/mypage";
    }

    @GetMapping("/user/update")
    public String userUpdate(Model model, Principal principal){
        if(principal!=null){
            User user = userService.findByUserId(principal.getName());
            model.addAttribute("user",new ViewUserResponse(user));
            return "user/updateForm";
        }else{
            return "redirect:/login";
        }
    }
    @GetMapping("/user/findId")
    public String findId(){
        return "user/findId";
    }

    @GetMapping("/user/findPassword")
    public String findPassword(){
        return "user/findPassword";
    }

    @PostMapping("/user/updatePwForm")
    public String updatePassword(ViewUserResponse response, Model model){
        model.addAttribute("userId", response.getUserId());
        return "user/updatePassword";
    }
}

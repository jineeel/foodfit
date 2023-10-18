package com.developer.foodfit.controller;

import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.user.AddUserRequest;
import com.developer.foodfit.dto.user.UpdateUserRequest;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/api/signup")
    public String join(AddUserRequest request){
        userService.save(request);
        return "redirect:/login";
    }
    // 회원 수정
    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> userUpdate(@PathVariable String id, @RequestBody UpdateUserRequest request){
        User updateUser = userService.update(id,request);
        return ResponseEntity.ok().body(updateUser);
    }
    //아이디 중복 체크
    @PostMapping("/user/checkId")
    @ResponseBody
    public Optional<User> checkUserId(@RequestParam("userId") String userId){
        return userService.checkUserId(userId);
    }

    //전화 번호 중복 체크
    @PostMapping("/user/checkPhone")
    @ResponseBody
    public Optional<User> checkPhone(@RequestParam("phone") String phone){
        return userService.checkPhone(phone);
    }

    //이메일 중복 체크
    @PostMapping("/user/checkEmail")
    @ResponseBody
    public Optional<User> checkEmail(@RequestParam("email") String email){
        return userService.checkEmail(email);
    }

    // 로그인 에러
    @GetMapping("/user/error")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, RedirectAttributes re) {
        re.addFlashAttribute("error", error);
        re.addFlashAttribute("exception", exception);

        return "redirect:/login";
    }

    // 아이디 찾기
    @PostMapping("/user/findId")
    @ResponseBody
    public Optional<User> findId(@RequestParam String username, @RequestParam String email, @RequestParam String phone) {
        return userService.findId(username, email, phone);
    }

    // 비밀번호 찾기 (재설정)
    @PostMapping("/user/findPassword")
    @ResponseBody
    public Optional<User> findPassword(@RequestParam String userId, @RequestParam String email, @RequestParam String phone) {
        return userService.findPassword(userId, email, phone);
    }

    // 비밀번호 수정
    @PostMapping("/user/updatePassword")
    public ResponseEntity<User> updatePassword(@RequestBody UpdateUserRequest request){
        User updatePassword = userService.updatePassword(request);
        return ResponseEntity.ok().body(updatePassword);
    }
}

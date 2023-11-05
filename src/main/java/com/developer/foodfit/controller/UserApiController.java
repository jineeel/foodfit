package com.developer.foodfit.controller;

import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.user.AddUserRequest;
import com.developer.foodfit.dto.user.UpdateUserRequest;
import com.developer.foodfit.dto.user.FindUserRequest;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    /* 회원 가입 */
    @PostMapping("/api/join")
    public ResponseEntity<User> join(AddUserRequest request){
        userService.save(request);
        return redirectLogin();
    }
    /* 회원 수정 */
    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> userUpdate(@PathVariable String id, @RequestBody UpdateUserRequest request){
        User updateUser = userService.update(id,request);
        return ResponseEntity.ok().body(updateUser);
    }
    /* 아이디 중복 체크 */
    @PostMapping("/api/user/checkId")
    public ResponseEntity<Boolean> checkUserId(@RequestBody String userId){
        return ResponseEntity.ok().body(userService.checkUserId(userId));
    }

    /* 전화 번호 중복 체크 */
    @PostMapping("/api/user/checkPhone")
    public ResponseEntity<Boolean> checkPhone(@RequestBody String phone){
        return ResponseEntity.ok().body(userService.checkPhone(phone));
    }

    /* 이메일 중복 체크 */
    @PostMapping("/api/user/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestBody String email){
        return  ResponseEntity.ok().body(userService.checkEmail(email));
    }

    /* 로그인 에러 */
    @GetMapping("/api/user/error")
    public ResponseEntity<User> loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("exception", exception);

        return redirectLogin();
    }

    /* 아이디 찾기 */
    @PostMapping("/api/user/findId")
    public ResponseEntity<User> findId(@RequestBody FindUserRequest request) {
        return ResponseEntity.ok().body(userService.findUserid(request));
    }

    /* 비밀번호 찾기 (재설정) */
    @PostMapping("/api/user/findPassword")
    public ResponseEntity<User> findPassword(@RequestBody FindUserRequest request) {
        return  ResponseEntity.ok().body(userService.findPassword(request));
    }

    /* 비밀번호 수정 */
    @PostMapping("/api/user/updatePassword")
    public ResponseEntity<User> updatePassword(@RequestBody UpdateUserRequest request){
        User updatePassword = userService.updatePassword(request);
        return ResponseEntity.ok().body(updatePassword);
    }

    /* login redirect */
    public ResponseEntity<User> redirectLogin(){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}

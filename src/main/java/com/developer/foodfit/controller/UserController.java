package com.developer.foodfit.controller;

import com.developer.foodfit.constant.OrderStatus;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.user.ViewUserResponse;
import com.developer.foodfit.service.OrderService;
import com.developer.foodfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    /* 로그인 페이지 */
    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    /* 회원가입 페이지 */
    @GetMapping("/user/join")
    public String join(){
        return "user/join";
    }

    /* 마이페이지 */
    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        User user = userService.findByUserId(principal.getName());

        Long orderCount = orderService.countByOrderStatus(OrderStatus.ORDER, user.getId());
        Long shippingCount = orderService.countByOrderStatus(OrderStatus.SHIPPING, user.getId());
        Long deliveredCount = orderService.countByOrderStatus(OrderStatus.DELIVERED, user.getId());
        Long cancelCount = orderService.countByOrderStatus(OrderStatus.CANCEL, user.getId());

        model.addAttribute("orderCount",orderCount);
        model.addAttribute("shippingCount",shippingCount);
        model.addAttribute("deliveredCount",deliveredCount);
        model.addAttribute("cancelCount",cancelCount);
        return "user/mypage";
    }

    /* 회원 수정 페이지 */
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
    /* 아이디찾기 페이지 */
    @GetMapping("/user/findId")
    public String findId(){
        return "user/findId";
    }

    /* 비밀번호 찾기 페이지 */
    @GetMapping("/user/findPassword")
    public String findPassword(){
        return "user/findPassword";
    }

    /* 비밀번호 수정 페이지 */
    @PostMapping("/user/updatePassword")
    public String updatePassword(ViewUserResponse response, Model model){
        model.addAttribute("userId", response.getUserId());
        return "user/updatePassword";
    }
}

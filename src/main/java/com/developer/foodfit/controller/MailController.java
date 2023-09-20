package com.developer.foodfit.controller;

import com.developer.foodfit.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/user/mail")
    public String sendMail(@RequestParam String email){
        int number = mailService.sendMail(email);
        String num = ""+number;
        return num;
    }
}

package com.developer.foodfit.controller;

import com.developer.foodfit.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/api/user/mail")
    public String sendMail(@RequestBody String email){

        int number = mailService.sendMail(email);
        String num = ""+number;
        System.out.println("!?"+num);
        return num;
    }
}

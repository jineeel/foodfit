package com.developer.foodfit.controller;

import com.developer.foodfit.dto.sms.MessageRequest;
import com.developer.foodfit.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Controller
public class SmsController {

    private final SmsService smsService;

    // TODO : 주석 제거
//    @ResponseBody
//    @PostMapping("/user/sms")
//    public int sendSms(@RequestBody MessageRequest message) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        int response = smsService.sendSms(message);
//        return response;
//    }

    // 테스트용 SMS
    // 테스트 후 삭제
    @ResponseBody
    @PostMapping("/user/sms")
    public int sendSms(@RequestBody MessageRequest message) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        int response = 123456;
        return response;
    }

}

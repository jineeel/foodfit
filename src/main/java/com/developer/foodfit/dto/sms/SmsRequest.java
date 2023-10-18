package com.developer.foodfit.dto.sms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class SmsRequest {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageRequest> messages;

    @Builder
    public SmsRequest(String type, String contentType, String countryCode, String from, String content, List<MessageRequest> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }
}

package com.developer.foodfit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MessageRequest {
    String to;

    public MessageRequest(String to){
        this.to=to;
    }

}

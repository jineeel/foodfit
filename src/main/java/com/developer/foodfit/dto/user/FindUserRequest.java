package com.developer.foodfit.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserRequest {
    private String userId;
    private String username;
    private String email;
    private String phone;
}

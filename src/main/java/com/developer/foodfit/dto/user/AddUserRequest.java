package com.developer.foodfit.dto.user;

import com.developer.foodfit.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {

    private String userId;
    private String password;
    private String username;
    private String phone;
    private String email;
    private Role role;

}

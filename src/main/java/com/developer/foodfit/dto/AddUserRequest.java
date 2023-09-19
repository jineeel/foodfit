package com.developer.foodfit.dto;

import com.developer.foodfit.constant.Role;
import lombok.Getter;

@Getter
public class AddUserRequest {

    private String userId;
    private String password;
    private String username;
    private String tel;
    private String email;
    private Role role;

}

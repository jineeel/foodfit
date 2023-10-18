package com.developer.foodfit.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
    private String userId;
    private String email;
    private String phone;
    private String password;
    private String username;
    private String zipcode;
    private String streetAdr;
    private String detailAdr;
}

package com.developer.foodfit.dto.user;

import com.developer.foodfit.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewUserResponse {
    private String userId;
    private String username;
    private String phone;
    private String email;
    private String providerId;
    private String zipcode;
    private String streetAdr;
    private String detailAdr;


    public ViewUserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.providerId = user.getProviderId();
        this.zipcode = user.getZipcode();
        this.streetAdr = user.getStreetAdr();
        this.detailAdr = user.getDetailAdr();
    }
}

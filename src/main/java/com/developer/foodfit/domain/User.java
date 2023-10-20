package com.developer.foodfit.domain;

import com.developer.foodfit.constant.Role;
import com.developer.foodfit.dto.user.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String email;
    private String phone;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String provider; //어떤 OAuth 인지
    private String providerId; //해당 OAuth의 key(id)

    private LocalDateTime createDate;

    private String zipcode;
    private String streetAdr;
    private String detailAdr;

    @Builder
    public User(Long id, String userId, String email, String phone, String password, String username, Role role,
                String provider, String providerId, LocalDateTime createDate, String zipcode, String streetAdr, String detailAdr) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }

    public void update(String email, String phone, String password, String username, String zipcode, String streetAdr, String detailAdr) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }

    public void update(UpdateUserRequest request) {
        this.email = request.getEmail();
        this.phone = request.getPhone();
        this.password = request.getPassword();
        this.username = request.getUsername();
        this.zipcode = request.getZipcode();
        this.streetAdr = request.getStreetAdr();
        this.detailAdr = request.getDetailAdr();
    }

    public void updatePassword(String password){
        this.password = password;
    }

}

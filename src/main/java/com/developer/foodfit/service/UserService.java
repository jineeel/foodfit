package com.developer.foodfit.service;

import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.AddUserRequest;
import com.developer.foodfit.dto.UpdateUserRequest;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .role(Role.USER)
                .createDate(LocalDateTime.now())
                .build()).getId();
    }

    public Optional<User> checkUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> checkPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public Optional<User> checkEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User update(String id, UpdateUserRequest request) {
        User target = userRepository.findByUserId(id).orElseThrow(()->new IllegalArgumentException("not found : "+id));
        String rawPassword = request.getPassword();
        String encPassword = "";
        if(target.getProviderId()==null){
            encPassword = bCryptPasswordEncoder.encode(rawPassword);
        }
//        target.update(request.getEmail(), request.getPhone(), encPassword, request.getUsername(), request.getZipcode(), request.getStreetAdr(), request.getDetailAdr());
        target.update(request);
        return target;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()-> new IllegalArgumentException("not found"));
    }

    public Optional<User> findId(String username, String email, String phone) {
        return userRepository.findByUsernameAndEmailOrPhone(username, email, phone);
    }

    public Optional<User> findPassword(String userId, String email, String phone) {
        return userRepository.findByUserIdAndEmailOrPhone(userId,email,phone);
    }

    @Transactional
    public User updatePassword(UpdateUserRequest request) {
        User target = userRepository.findByUserId(request.getUserId()).orElseThrow(()->new IllegalArgumentException("not found"));
        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        target.updatePassword(encPassword);

        return target;
    }
}

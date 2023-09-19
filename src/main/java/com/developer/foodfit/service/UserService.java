package com.developer.foodfit.service;

import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.AddUserRequest;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public boolean checkUserId(String userId) {
        Optional<User> findUserId = userRepository.findByUserId(userId);
        return !findUserId.isPresent();
    }
}

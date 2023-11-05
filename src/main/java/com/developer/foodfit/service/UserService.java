package com.developer.foodfit.service;

import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.user.AddUserRequest;
import com.developer.foodfit.dto.user.FindUserRequest;
import com.developer.foodfit.dto.user.UpdateUserRequest;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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

    //아이디 중복 체크
    public boolean checkUserId(String userId) {
        return userRepository.findByUserId(userId).isEmpty();
    }

    //전화번호 중복 체크
    public boolean checkPhone(String phone) {
        return userRepository.findByPhone(phone).isEmpty();
    }

    //이메일 중복 체크
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    //회원 수정
    @Transactional
    public User update(String id, UpdateUserRequest request) {
        User target = userRepository.findByUserId(id).orElseThrow(()->new IllegalArgumentException("not found"));
        String rawPassword = request.getPassword();
        String encPassword = "";
        if(target.getProviderId()==null){
            encPassword = bCryptPasswordEncoder.encode(rawPassword);
        }
        target.update(request,encPassword);
        return target;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()-> new IllegalArgumentException("not found"));
    }

    //아이디 찾기
    public User findUserid(FindUserRequest request) {
        User user=null;
        System.out.println(request.getUsername());
        if(request.getPhone().equals("")){
            user = userRepository.findByUsernameAndEmail(request.getUsername(), request.getEmail());
        }else if(request.getEmail().equals("")){
            user =  userRepository.findByUsernameAndPhone(request.getUsername(), request.getPhone());
        }
        return user;
    }

    //비밀번호 변경할 user 찾기
    public User findPassword(FindUserRequest request) {
        User user=null;
        if(request.getPhone().equals("")){
            user = userRepository.findByUserIdAndEmail(request.getUserId(), request.getEmail());
        }else if(request.getEmail().equals("")){
            user =  userRepository.findByUserIdAndPhone(request.getUserId(), request.getPhone());
        }
        return user;
    }

    //비밀번호 수정
    @Transactional
    public User updatePassword(UpdateUserRequest request) {
        User target = userRepository.findByUserId(request.getUserId()).orElseThrow(()->new IllegalArgumentException("not found"));
        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        target.updatePassword(encPassword);

        return target;
    }

}

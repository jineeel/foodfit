package com.developer.foodfit.service;

import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.PrincipalDetails;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId){
        User findUser = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다"));
        return new PrincipalDetails(findUser);
    }


}

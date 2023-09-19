package com.developer.foodfit.service;

import com.developer.foodfit.domain.User;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId){
        return (UserDetails) userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("userId"));
    }
}

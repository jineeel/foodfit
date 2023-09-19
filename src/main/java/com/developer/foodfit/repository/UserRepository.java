package com.developer.foodfit.repository;


import com.developer.foodfit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(String userId);
    User findByEmail(String email);
    User findByProviderId(String providerId);
}

package com.developer.foodfit.repository;


import com.developer.foodfit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    User findByProviderId(String providerId);

    Optional<User> findByPhone(String phone);

    Optional<User> findByUsernameAndEmailOrPhone(String username, String email, String phone);

    Optional<User> findByUserIdAndEmailOrPhone(String userId, String email, String phone);

}

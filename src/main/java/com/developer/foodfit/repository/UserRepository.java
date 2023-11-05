package com.developer.foodfit.repository;


import com.developer.foodfit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(String userId);
//    User findByUserId(String userId);

    Optional<User> findByEmail(String email);

    User findByProviderId(String providerId);

    Optional<User> findByPhone(String phone);

    User findByUsernameAndEmail(String username, String email);
    User findByUsernameAndPhone(String username, String phone);

    User findByUserIdAndEmail(String userId, String email);
    User findByUserIdAndPhone(String userId,  String phone);

}

package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByChatId(String chatId);

    Optional<UserDetails> findByPhoneNumber(String phone);

    UserDetails findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailAndOtpCode(String email, Integer code);
}
package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByChatId(String chatId);
//    Optional<UserDetails> findByPhone(String phone);

    Optional<User> findByEmail(String email);

}

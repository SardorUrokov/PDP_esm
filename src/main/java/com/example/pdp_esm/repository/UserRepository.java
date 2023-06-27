package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetails> findByPhoneNumber(String phone);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailAndVerifyCode(@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") String email, String verifyCode);

    @Query(value = "select users.dType from users where users.phone_number = :phoneNumber",
            nativeQuery = true)
    String getUsersDType(String phoneNumber);
}
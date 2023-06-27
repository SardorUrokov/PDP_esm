package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.ReserveUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReserveUsersRepository extends JpaRepository<ReserveUsers, Long> {

    Optional<ReserveUsers> findByOtpCode(String otp);
}
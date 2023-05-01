package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

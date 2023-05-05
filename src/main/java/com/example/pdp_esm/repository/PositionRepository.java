package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
    boolean existsByName(String name);
}

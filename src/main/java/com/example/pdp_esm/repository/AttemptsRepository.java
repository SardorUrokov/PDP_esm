package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttemptsRepository extends JpaRepository<Attempts, Long> {

    Optional<Attempts> findByStudent_IdAndModules_Id(Long student_id, Long module_id);

    Optional<Attempts> findByStudent_Id(Long student_id);

    Optional<Attempts> findByAttempts(Integer attempts);
}
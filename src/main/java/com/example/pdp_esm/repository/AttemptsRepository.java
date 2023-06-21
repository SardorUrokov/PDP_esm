package com.example.pdp_esm.repository;

import java.util.Optional;
import com.example.pdp_esm.entity.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptsRepository extends JpaRepository<Attempts, Long> {
    Optional<Attempts> findByStudent_IdAndModules_Id(Long student_id, Long module_id);

    boolean existsByStudentIdAndModules_Id(Long student_id, Long modules_id);

    Optional<Attempts> findByStudent_Id(Long student_id);

    Optional<Attempts> findByAttempts(Integer attempts);
}
package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    boolean existsByStudentId(Long student_id);
}

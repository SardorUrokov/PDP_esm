package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

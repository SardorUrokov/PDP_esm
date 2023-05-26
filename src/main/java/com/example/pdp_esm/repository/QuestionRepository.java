package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByActiveTrue();

    Optional<Question> findByQuestionText(String question);

    List<Question> findAllByActive(Boolean active);
}
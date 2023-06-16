package com.example.pdp_esm.repository.test;

import com.example.pdp_esm.entity.test.AnswerTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepositoryTest extends JpaRepository<AnswerTest, Long> {

    Optional<AnswerTest> findByQuestion_IdAndStatusTrue(Long question_id);

    List<AnswerTest> findByQuestion_IdOrderByPosition(Long question_id);

    List<AnswerTest> findByQuestion_IdOrderById(Long question_id);

    boolean existsByQuestion_IdAndPositionAndStatus(Long question_id, Integer position, boolean status);
}
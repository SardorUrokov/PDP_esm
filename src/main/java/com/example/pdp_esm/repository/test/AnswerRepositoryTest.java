package com.example.pdp_esm.repository.test;

import com.example.pdp_esm.entity.test.AnswerTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepositoryTest extends JpaRepository<AnswerTest, Long> {

    Optional<AnswerTest> findByQuestion_IdAndStatusTrue(Long question_id);
    List<AnswerTest> findByQuestion_Id(Long question_id);
}
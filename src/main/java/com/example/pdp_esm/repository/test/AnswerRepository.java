package com.example.pdp_esm.repository.test;

import com.example.pdp_esm.entity.test.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByQuestion_IdAndStatusTrue(Long question_id);
    List<Answer> findByQuestion_Id(Long question_id);
}
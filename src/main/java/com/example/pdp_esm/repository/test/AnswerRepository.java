package com.example.pdp_esm.repository.test;

import com.example.pdp_esm.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByQuestion_IdAndStatusTrue(Long question_id);

    List<Answer> findByQuestion_IdOrderByPosition(Long question_id);

    List<Answer> findByQuestion_IdOrderById(Long question_id);

    boolean existsByQuestion_IdAndPositionAndStatus(Long question_id, Integer position, boolean status);
}
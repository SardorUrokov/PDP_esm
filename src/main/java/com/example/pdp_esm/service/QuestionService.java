package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface QuestionService {

    ApiResponse<?> createQuestion(QuestionDTO questionDTO);

    ApiResponse<?> getAllQuestions();

    ApiResponse<?> getAllByActive(String active);

    ApiResponse<?> getOneQuestion(Long question_id);

    ApiResponse<?> updateQuestion(Long question_id, QuestionDTO questionDTO);

    ApiResponse<?> deleteQuestion(Long question_id);
}

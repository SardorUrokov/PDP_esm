package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.*;
import com.example.pdp_esm.dto.AnswerDTO;

public interface AnswerService {

    ApiResponse<?> createAnswer(AnswerDTO answerDTO);

    ApiResponse<?> getAllAnswers();

    ApiResponse<?> getOneAnswer(Long id);

    ApiResponse<?> updateAnswer(Long id, AnswerDTO answerDTO);

    ApiResponse<?> deleteAnswer(Long id);
}
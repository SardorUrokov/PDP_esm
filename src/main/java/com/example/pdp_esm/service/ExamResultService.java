package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface ExamResultService {

    ApiResponse<?> createExamResult(ExamResultDTO examResultDTO);

    ApiResponse<?> getAllExamResult();

    ApiResponse<?> getOneExamResult(Long result_id);

    ApiResponse<?> updateExamResult(Long result_id, ExamResultDTO resultDTO);

    ApiResponse<?> deleteExamResult(Long id);

}

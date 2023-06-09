package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ExamineInfoDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface ExamineInfoService {
    ApiResponse<?> create(ExamineInfoDTO dto);

    ApiResponse<?> readAll();

    ApiResponse<?> readOne(Long id);

    ApiResponse<?> byStartsDate(String date);

    ApiResponse<?> update(Long id, ExamineInfoDTO dto);

    ApiResponse<?> delete(Long id);
}
package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.ApiResponse;

public interface PositionService {
    ApiResponse<?> createPosition(String positionName);

    ApiResponse<?> getAllPositions();

    ApiResponse<?> getOnePosition(Long id);

    ApiResponse<?> deletePosition(Long id);
}
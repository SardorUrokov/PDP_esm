package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.AbsModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface AbsModuleService {

    ApiResponse<?> createAbsModule(AbsModuleDTO absModuleDTO);

    ApiResponse<?> getAllAbsModules();

    ApiResponse<?> getOneAbsModule(Long id);

    ApiResponse<?> getByCourseId(Long id);

    ApiResponse<?> getByModule(Long id);

    ApiResponse<?> updateAbsModule(Long id, AbsModuleDTO dto);

    ApiResponse<?> deleteAbsModule(Long id);
}
package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface ModulesService {

    ApiResponse<?> createModule(ModuleDTO moduleDTO);

    ApiResponse<?> getAllModule();

    ApiResponse<?> getByModuleId(Long id);

    ApiResponse<?> getByOrdinalNumber(Long ordinalNumber);

    ApiResponse<?> getByCourseId(Long id);

    ApiResponse<?> updateModule(Long id, ModuleDTO moduleDTO);

    ApiResponse<?> deleteModule(Long id);
}
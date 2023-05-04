package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.TeacherDTO;

public interface TeacherService {
    ApiResponse<?> create(TeacherDTO teacherDTO);

    ApiResponse<?> getAll();
}

package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.StudentDTO;
import com.example.pdp_esm.dto.TeacherDTO;

public interface StudentService {
    ApiResponse<?> create(StudentDTO studentDTO);

    ApiResponse<?> getAll();
}

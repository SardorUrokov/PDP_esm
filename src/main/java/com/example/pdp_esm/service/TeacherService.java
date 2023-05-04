package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.TeacherDTO;

public interface TeacherService {
    ApiResponse<?> createTeacher(TeacherDTO teacherDTO);

    ApiResponse<?> getAllTeachers();

    ApiResponse<?> getOneTeacher(Long teacher_id);

    ApiResponse<?> updateTeacher(Long teacher_id, TeacherDTO teacherDTO);

    ApiResponse<?> deleteTeacher(Long teacher_id);

    ApiResponse<?> getAllActiveFalseTeachers();

    ApiResponse<?> getOneActiveFalseTeacher(Long teacher_id);
}

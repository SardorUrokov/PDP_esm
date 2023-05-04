package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.StudentDTO;

public interface StudentService {
    ApiResponse<?> createStudent(StudentDTO studentDTO);
    ApiResponse<?> getAllStudents();
    ApiResponse<?> getOneStudent(Long student_id);

    ApiResponse<?> updateStudent(Long student_id, StudentDTO studentDTO);

    ApiResponse<?> deleteStudent(Long student_id);

    ApiResponse<?> getAllActiveFalseStudents();

    ApiResponse<?> getOneActiveFalseStudent(Long student_id);
}

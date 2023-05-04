package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.CourseDTO;

public interface CourseService {
    ApiResponse<?> createCourse (CourseDTO courseDTO);

    ApiResponse<?> getAllCourses();

    ApiResponse<?> getOneCourse(Long course_id);

    ApiResponse<?> updateCourse(Long rating_id, CourseDTO courseDTO);

    ApiResponse<?> deleteCourse(Long course_id);

    ApiResponse<?> getAllActiveFalseCourses();

    ApiResponse<?> getOneActiveFalseCourse(Long course_id);
}
package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.CourseDTO;
import com.example.pdp_esm.service.Implements.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseServiceImpl courseService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        ApiResponse<?> response = courseService.createCourse(courseDTO);

        if (response.isSuccess()) log.warn("Creating Course! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity
                .status(response.isSuccess() ? 201 : 409)
                .body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCourses() {
        ApiResponse<?> response = courseService.getAllCourses();
        log.warn("Getting All Courses List! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCourse(@PathVariable Long id) {
        ApiResponse<?> response = courseService.getOneCourse(id);

        if (response.isSuccess()) log.info("Getting Course {} with id! -> {}", response.getData(), id);
        else log.error("Course not found {} with id! -> {}", response.getData(), id);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        ApiResponse<?> response = courseService.updateCourse(id, courseDTO);
        log.warn("Course with id {} is updated! -> {}", id, response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = courseService.deleteCourse(id);
        log.warn("Course with id {} is terminated! -> {}", id, response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/deletedCourses")
    public ResponseEntity<?> getAllActiveFalseCourses() {
        ApiResponse<?> response = courseService.getAllActiveFalseCourses();
        log.warn("Getting All Terminated Courses List! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/deletedCourses/{id}")
    public ResponseEntity<?> getOneActiveFalseCourse(@PathVariable Long id) {
        ApiResponse<?> response = courseService.getOneActiveFalseCourse(id);
        log.warn("Getting One Terminated Course! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}